package com.freeland.service.wallet;

import com.freeland.dao.po.EthHDWallet;

/**
 *
 * @author heiqie
 * @date 2018/7/26
 */
public interface WalletService {

    EthHDWallet generateHDWallet(String password);
}




