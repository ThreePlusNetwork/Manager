package com.freeland.controller;

import com.freeland.constant.InteractionType;
import com.freeland.dao.po.EthHDWallet;
import com.freeland.dao.po.User;
import com.freeland.dao.po.UserPossession;
import com.freeland.model.Interaction;
import com.freeland.model.ResponseWrapper;
import com.freeland.model.TokenTransfer;
import com.freeland.model.WalletCreatation;
import com.freeland.service.interaction.InteractionService;
import com.freeland.service.user.UserPossessionService;
import com.freeland.service.user.UserService;
import com.freeland.service.wallet.WalletService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author heiqie
 * @date 2018/7/11
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPossessionService userPossessionService;

    @Autowired
    private InteractionService interactionService;

    @Autowired
    private WalletService walletService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "测试", notes = "测试", httpMethod = "GET")
    public ResponseWrapper<String> test() {
        return ResponseWrapper.success("你可以访问");
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ApiOperation(value = "创建新用户", notes = "创建新的用户", httpMethod = "POST")
    public ResponseWrapper<String> addNewUser(@NotBlank @RequestParam(name = "name") String name,
                                              @NotBlank @RequestParam(name = "password") String password) {
        boolean success = userService.register(User.builder().username(name).password(password).build());
        if (success) {
            return ResponseWrapper.success("success");
        }
        return ResponseWrapper.error("该用户已经被注册");
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户信息详情", notes = "通过用户id获取用户信息", httpMethod = "GET")
    public ResponseWrapper<User> retrieveUsers(@NotBlank @RequestParam(name = "id") Long id) {
        return ResponseWrapper.success(userService.findById(id));
    }


    @RequestMapping(value = "/user/gold", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户金币", notes = "通过用户id获取用户金币数量", httpMethod = "GET")
    public Long retrieveUserGold(@NotBlank @RequestParam(name = "id") Long id) {
        UserPossession userPossession = userPossessionService.findByUserId(id);
        if (userPossession == null) {
            return null;
        }
        return userPossession.getGold();
    }

    @RequestMapping(value = "/user/interaction", method = RequestMethod.POST)
    @ApiOperation(value = "用户交互接口", notes = "用户交互接口", httpMethod = "POST")
    public ResponseWrapper<Long> interaction(@RequestBody Interaction interaction) {
        Long updatedGoldBalance = interactionService.handle(interaction.getConnectedUserId(), InteractionType.getByNumber(interaction.getInteractionType()));
        return ResponseWrapper.success(updatedGoldBalance);
    }

    @RequestMapping(value = "/user/token", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户token数量", notes = "通过用户id获取用户token数量", httpMethod = "GET")
    public Long retrieveUserToken(@NotBlank @RequestParam(name = "id") Long id) {
        UserPossession userPossession = userPossessionService.findByUserId(id);
        if (userPossession == null) {
            return null;
        }
        return userPossession.getToken();
    }

    @RequestMapping(value = "/user/token/transfer", method = RequestMethod.POST)
    @ApiOperation(value = "用户转账token", notes = "用户转账token", httpMethod = "POST")
    public ResponseWrapper<String> transferToken(@RequestBody TokenTransfer tokenTransfer) {
        return ResponseWrapper.success("ok");
    }


    @RequestMapping(value = "/user/bounty", method = RequestMethod.POST)
    @ApiOperation(value = "水龙头测试使用", notes = "水龙头测试使用", httpMethod = "POST")
    public ResponseWrapper<Long> requestBounty(@NotBlank @RequestParam(name = "id") Long id) {
        UserPossession userPossession = userPossessionService.requestBounty(id);
        return ResponseWrapper.success(userPossession.getGold());
    }

    /**
     * {
     *     "errorMsg": null,
     *     "data": {
     *         "privateKey": "fbe23d014b8ab26b8d46baa52d1af9d71d10a911870d8833022de568e3f15295",
     *         "publicKey": "9ae443e59f436b4ccdb0a63052d82d98c207c2aa5a01b4e104eb296d21158dbeb194c46872a3339238a4feb76a2d8a7d52d8a4566e78a990b898af2c11b8c446",
     *         "mnemonic": [
     *             "chimney",
     *             "tail",
     *             "option",
     *             "eight",
     *             "people",
     *             "elevator",
     *             "casual",
     *             "rocket",
     *             "palace",
     *             "more",
     *             "clock",
     *             "pact"
     *         ],
     *         "mnemonicPath": "M/44H/60H/0H/0/0",
     *         "address": "0xb4efe1848fec387e3aecfa0c3513a0f2a800d5ca",
     *         "keystore": "{\"address\":\"b4efe1848fec387e3aecfa0c3513a0f2a800d5ca\",\"id\":\"b2008895-da91-46a0-a50a-b4d1f62f5b6d\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"cd7ae8b4ed5315f762f286a9cad007dd3b55f913482fb02db4a70a060c9dcf77\",\"cipherparams\":{\"iv\":\"3fcc9c7b1e586eeba2695f5622b19721\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":4096,\"p\":6,\"r\":8,\"salt\":\"f781ce586526a2b62c70108fd867838a4c73a81e9b7df5c80522a57d0eb2ccd9\"},\"mac\":\"d78f8788dfd8be65ab0823128a3a9b91c27465be5d83e74b023b45c574980fd1\"}}"
     *     },
     *     "code": 1
     * }
     */

    @RequestMapping(value = "/user/createWallet", method = RequestMethod.POST)
    @ApiOperation(value = "创建钱包", notes = "创建钱包", httpMethod = "POST")
    public ResponseWrapper<EthHDWallet> requestBounty(@RequestBody WalletCreatation walletCreatation) {

        EthHDWallet ethHDWallet = walletService.generateHDWallet(walletCreatation.getPassword());
        return ResponseWrapper.success(ethHDWallet);
    }


}
