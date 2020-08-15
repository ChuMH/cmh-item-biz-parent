package test.template.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @description：转账业务
 */
@Slf4j
class TransferBusiness extends BankService{
    /**
     * 用户到窗口办理业务
     */
    protected Boolean handleBusiness(String name){
        log.info("用户：{}办理转账业务",name);
        return true;
    }

    public void service(String name){
        business(name);
    }

    public static void main(String[] args) {
        TransferBusiness test = new TransferBusiness();
        test.service("小明");
    }
}
