package test.template.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @description：银行服务
 */
@Slf4j
abstract class BankService {
    /**
     * 银行主营业务
     */
    public final void business(String name){
        log.info("用户：{}进入银行",name);
        //取号
        if(this.lineUp(name)){
            handleBusiness(name);
        }
        this.score(name);
        log.info("用户：{}离开银行",name);
    }
    /**
     * 排队取号
     */
    private Boolean lineUp(String name){
        log.info("用户：{}排队取号",name);
        return true;
    }
    /**
     * 给工作人员评分
     */
    private void  score(String name){
        log.info("用户：{}对本次服务评分",name);
    }
    /**
     * 用户到窗口办理业务
     */
    protected Boolean handleBusiness(String name){
        log.info("用户：{}到窗口办理业务");
        return true;
    }
}
