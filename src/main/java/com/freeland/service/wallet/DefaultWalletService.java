package com.freeland.service.wallet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freeland.dao.po.EthHDWallet;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.crypto.*;
import org.bitcoinj.wallet.DeterministicSeed;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author heiqie
 * @date 2018/7/26
 */
@Slf4j
@Service
public class DefaultWalletService implements WalletService{

    private static SecureRandom secureRandom = new SecureRandom();

    /**
     * 通用的以太坊基于bip44协议的助记词路径 （imtoken jaxx Metamask myetherwallet）
     */
    private static String ETH_TYPE = "m/44'/60'/0'/0/0";


    @Override
    public EthHDWallet generateHDWallet(String password) {
        return generateByMnemonic(ETH_TYPE,password);
    }


    private EthHDWallet generateByMnemonic(String path, String password){
        if (!path.startsWith("m") && !path.startsWith("M")) {
            //参数非法
            return null;
        }
        String[] pathArray = path.split("/");
        if (pathArray.length <= 1) {
            //内容不对
            return null;
        }

        if (password.length() < 8) {
            //密码过短
            return null;
        }

        String passphrase = "";
        long creationTimeSeconds = System.currentTimeMillis() / 1000;
        DeterministicSeed ds = new DeterministicSeed(secureRandom, 128, passphrase, creationTimeSeconds);
        return createEthWallet(ds, pathArray, password);
    }


    private EthHDWallet createEthWallet(DeterministicSeed ds, String[] pathArray, String password){
        //seed
        byte[] seed = ds.getSeedBytes();
        log.info("seed hex string: {}", Numeric.toHexString(seed));

        //mnemonic
        List<String> mnemonic = ds.getMnemonicCode();
        log.info("mnemonic:{}",String.join(" ",mnemonic));

        try {
            //助记词种子
            byte[] mnemonicSeedBytes = MnemonicCode.INSTANCE.toEntropy(mnemonic);
            log.info("generate seed by mnemonic :{}",Numeric.toHexString(mnemonicSeedBytes));
            ECKeyPair mnemonicKeyPair = ECKeyPair.create(mnemonicSeedBytes);
            WalletFile walletFile = Wallet.createLight(password, mnemonicKeyPair);
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            //存这个keystore 用完后删除
            String jsonStr = objectMapper.writeValueAsString(walletFile);
            System.out.println("mnemonic keystore " + jsonStr);
            //验证
            WalletFile checkWalletFile = objectMapper.readValue(jsonStr, WalletFile.class);
            ECKeyPair ecKeyPair = Wallet.decrypt(password, checkWalletFile);
            byte[] checkMnemonicSeedBytes = Numeric.hexStringToByteArray(ecKeyPair.getPrivateKey().toString(16));
            System.out.println("验证助记词种子 "
                    + Arrays.toString(checkMnemonicSeedBytes));
            List<String> checkMnemonic = MnemonicCode.INSTANCE.toMnemonic(checkMnemonicSeedBytes);
            System.out.println("验证助记词 " + Arrays.toString(checkMnemonic.toArray()));

        } catch (MnemonicException.MnemonicLengthException | MnemonicException.MnemonicWordException | MnemonicException.MnemonicChecksumException | CipherException | IOException e) {
            log.error("error,",e);
        }
        DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(seed);
        for (int i = 1; i < pathArray.length; i++) {
            ChildNumber childNumber;
            if (pathArray[i].endsWith("'")) {
                int number = Integer.parseInt(pathArray[i].substring(0,
                        pathArray[i].length() - 1));
                childNumber = new ChildNumber(number, true);
            } else {
                int number = Integer.parseInt(pathArray[i]);
                childNumber = new ChildNumber(number, false);
            }
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber);
        }
        System.out.println("path " + dkKey.getPathAsString());

        ECKeyPair keyPair = ECKeyPair.create(dkKey.getPrivKeyBytes());
        System.out.println("eth privateKey " + keyPair.getPrivateKey().toString(16));
        System.out.println("eth publicKey " + keyPair.getPublicKey().toString(16));

        EthHDWallet ethHDWallet = null;
        try {
            WalletFile walletFile = Wallet.createLight(password, keyPair);
            System.out.println("eth address " + "0x" + walletFile.getAddress());
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            //存
            String jsonStr = objectMapper.writeValueAsString(walletFile);
            System.out.println("eth keystore " + jsonStr);

            ethHDWallet = new EthHDWallet(keyPair.getPrivateKey().toString(16),
                    keyPair.getPublicKey().toString(16),
                    mnemonic, dkKey.getPathAsString(),
                    "0x" + walletFile.getAddress(), jsonStr);
        } catch (CipherException | JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("ethHDWallet:{}",ethHDWallet);

        return ethHDWallet;
    }
}
