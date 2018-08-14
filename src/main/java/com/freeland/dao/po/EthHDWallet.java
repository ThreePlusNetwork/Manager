package com.freeland.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author heiqie
 * @date 2018/7/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EthHDWallet {
    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 助记词
     */
    private List<String> mnemonic;

    /**
     * 助记词path
     */
    private String mnemonicPath;

    /**
     * 钱包地址
     */
    private String address;

    /**
     * keystore 对称加密后的秘钥
     */
    private String keystore;
}
