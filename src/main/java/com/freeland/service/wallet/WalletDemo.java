package com.freeland.service.wallet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freeland.dao.po.EthHDWallet;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.crypto.*;
import org.bitcoinj.wallet.DeterministicSeed;
import org.springframework.util.StringUtils;
import org.web3j.crypto.*;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

/**
 * @author heiqie
 */
@Slf4j

public class WalletDemo {
    private Web3j web3j;
    private Credentials credentials;

    public static void main(String[] args) throws Exception {
        new WalletDemo().web3jInit();
    }


    private void web3jInit() throws Exception {
        log.info("init web3j");
        //生成助记词
        ethClient();//连接以太坊客户端 connect to eth wallet
//        createAccount();//创建冷钱包 generate cold wallet
//        loadWallet();//加载钱包 load wallet
//        loadByMnemonic("capable demand jungle assist dignity sheriff wine rose middle physical harvest key","");
//        generateBip39WalletAndLoadByMnemonic();
//        getBalanceOfETH();//查询账户余额 query balance
//        transferTo();//转账到指定地址 transfer to specific address
//        generateMnemonic();
    }

    private void generateBip39WalletAndLoadByMnemonic() throws CipherException, IOException {
        String password = "";
        Bip39Wallet bip39Wallet = generateBip39Wallet(password);
        loadByMnemonic(bip39Wallet.getMnemonic(), password);

    }


    private Bip39Wallet generateBip39Wallet(String password) throws CipherException, IOException {
        final String walletPath = "./wallet";
        Bip39Wallet bip39Wallet = WalletUtils.generateBip39Wallet(password, new File(walletPath));
        log.info("walletName:{}", bip39Wallet.getFilename());
        log.info("mnemonic:{}",bip39Wallet.getMnemonic());
        return bip39Wallet;
    }

    private  Credentials loadByMnemonic(String mnemonic,String password){
        Credentials credentials = WalletUtils.loadBip39Credentials(password,mnemonic);
        log.info("address:{}", credentials.getAddress());
        log.info("public key:{}", credentials.getEcKeyPair().getPublicKey());
        log.info("private key:{}", credentials.getEcKeyPair().getPrivateKey());
        return credentials;
    }



    /**
     * 以太坊客户端
     *
     * @throws IOException error
     */
    private void ethClient() throws IOException {
        //连接方式1：使用 infura 提供的客户端 use proxy
        //连接方式2：使用本地客户端 use local client
        web3j = Web3j.build(new HttpService("https://ropsten.infura.io/Jj5m4mVrrHBYBH5hyDNc"));
        String web3ClientVersion = web3j.web3ClientVersion().send().getWeb3ClientVersion();
        log.info("version:{}", web3ClientVersion);
    }


    /**
     * generate wallet
     *
     * @throws Exception error
     */
    private void createAccount() throws Exception {
        //钱包文件保持路径，请替换位自己的某文件夹路径
        final String walletPath = "./wallet";
        Bip39Wallet bip39Wallet = WalletUtils.generateBip39Wallet("",new File(walletPath));
//        String walletFileName = WalletUtils.generateNewWalletFile("123456", new File(walletPath), false);
        log.info("walletName:{}", bip39Wallet);
    }


    /**
     * load wallet 加载已有的钱包
     *
     * @throws Exception error
     */
    private void loadWallet() throws Exception {
        //钱包路径
        String walletFilePath = "./wallet/UTC--2018-07-26T04-40-56.56000000Z--3755172ff2fa32f1d287dc1c88e9f86f67710c3b.json";
        String passWord = "123456";
        credentials = WalletUtils.loadCredentials(passWord, walletFilePath);
        String address = credentials.getAddress();
        BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

        log.info("address:{}", address);
        log.info("public key:{}", publicKey);
        log.info("private key:{}", privateKey);
    }

    /**
     * 转移以太坊
     *
     * @throws Exception error
     */
    private void transferTo() throws Exception {
        if (web3j == null) {
            return;
        }
        if (credentials == null) {
            return;
        }
        //开始发送0.01 =eth到指定地址
        String addressTo = "0x41F1dcbC0794BAD5e94c6881E7c04e4F98908a87";
        TransactionReceipt send = Transfer.sendFunds(web3j, credentials, addressTo, BigDecimal.ONE, Convert.Unit.FINNEY).send();

        log.info("Transaction complete");
        log.info("trans hash:{}", send.getTransactionHash());
        log.info("from :{}", send.getFrom());
        log.info("to:{}", send.getTo());
        log.info("gas used:{}", send.getGasUsed());
        log.info("status:{} ", send.getStatus());
    }

    /**
     * 查询地址以太坊余额
     *
     * @throws IOException error
     */
    private void getBalanceOfETH() throws IOException {

        if (web3j == null) {
            return;
        }
        //等待查询余额的地址
        String address = "0x6dA009d676fCAaF4DfaeAD6aF6E29349006f8d40";
        //第二个参数：区块的参数，建议选最新区块
        EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
        //格式转化 wei-ether
        String balanceETH = Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER).toPlainString().concat(" ether");
        log.info("balanceETH:{}",balanceETH);
    }







}
