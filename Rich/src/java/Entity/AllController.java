/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author 17119
 */
@Named("AllController")
@SessionScoped
public class AllController implements Serializable {

    @EJB
    private Entity.ProjectFacade projFacade;
    @EJB
    private Entity.InvesteProjFacade investFacade;
    @EJB
    private Entity.UserFacade userFacade;
    @EJB
    private Entity.GuardProjFacade guardFacade;

    //用于存放当前登录的用户数据
    private User current = null;
    //当前请求详情的项目
    private Project detail = null;
    //登录数据
    private User tempUser;
    //注册数据
    private Project tempro;
    //种类
    private String kind = "";
    //省份
    private String prov = "";
    //下拉框
    //类别
    private List<SelectItem> cataItems = null;
    //地区
    private List<SelectItem> provItems = null;

    /*tangkexin*/
    //input绑定
    private String userName;
    private String userTel;
    private String userPsw;
    private String applicantName;
    private String applicantIDcard;
    private String applicantMail;
    //private Date applDate;  获取当前时间
    private String projCatelog;
    private String projClass;
    private String projName;
    private String projProvince;
    private String projCity;
    private String projRegion;
    private String projAddr;

    public AllController() {
        System.out.print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
        current = new User("13671021552", "wqm", "password");
        detail = new Project();
        Date date = new Date(2009, 4, 2);
        //测试数据
        detail.setProjName("脆皮鸭文学");
        detail.setProjId(1);
        detail.setProjClass("民间文学");
        detail.setApplDate(date);
        detail.setProjProvince("北京");
        detail.setApplicantName("隋小雨");
        detail.setApplicantMail("13611393447@163.com");
        detail.setProjAddr("北京邮电大学");
        detail.setProjDepict("布洛陀是壮族先民口头文学中的神话人物，是创世神、始祖神和道德神。《布洛陀》是壮族的长篇诗体创世神话，主要记述布洛陀开天辟地、创造人类的丰功伟绩，自古以来以口头方式在广西壮族自治区田阳县一带传承。大约从明代起，在口头传唱的同时，也以古壮字书写的形式保存下来，其中有一部分变成壮族民间麽教的经文。\n"
                + "　　《布洛陀》的内容包括布洛陀创造天地、造人、造万物、造土皇帝、造文字历书和造伦理道德六个方面，反映了人类从茹毛饮血的蒙昧时代走向农耕时代的历史，以及壮族先民氏族部落社会的情况，在历史学、文学、宗教学、古文字学、音韵学和音乐学研究等方面有一定的学术价值。\n"
                + "　　布洛陀口传诗体创世神话在内容上具有原生性特点，在漫长的口头传承过程中，经过一代代的不断加工和锤炼，艺术性也得到了完善和提高。它不仅可以帮助人们认识历史、满足人们的生活需求，还具有教化的作用。\n"
                + "　　由于历史及其他各种原因，今天《布洛陀》已面临失传的危机，需要采取普查、建档、研究、出版等手段，并通过建立布洛陀文化生态保护村、唱诵队、传习馆以及在相关学校开办传习班等方式加以保护，使其能在现代化社会条件下继续得到传承。");
    }

//--------------------------------------------------------------------tkx----------------------------------------------------------------------------------
    public String logUp() {
        if (getUserFacade().checklogup(userTel) != null) {
            //弹出手机号已注册，转到登录界面
        } else {
            User u = new User();
            u.setUserName(userName);
            u.setUserPsw(userPsw);
            u.setUserTel(userTel);
            getUserFacade().create(u);
        }
        return "/log_in.xhtml"; //注册成功返回登陆界面
    }

    public String logIn() {
        if (userTel.equals("00000000000") && userPsw.equals("000000")) {
            return "manager.xhtml";     //管理员识别
        } else if (getUserFacade().checkLogin(userName, userTel) == null) {
            if (getUserFacade().checklogup(userTel) == null) {
                return "log_up.xhtml";  //手机号未注册，转至注册页面
            } else {
                return "index.xhtml";  //密码错误
            }
        }
        current.setUserName(userName);
        current.setUserPsw(userPsw);
        current.setUserTel(userTel);
        return "/index.xhtml"; //登陆成功返回首页
    }

     public String creartPro(){
       tempro = new Project();
       tempro.setApplDate(new Date());
       tempro.setApplicantIDcard(applicantIDcard);
       tempro.setApplicantMail(applicantMail);
       tempro.setProjAddr(projAddr);
       tempro.setProjCatelog(projCatelog);
       tempro.setApplicantName(applicantName);
       tempro.setProjCity(projCity);
       tempro.setProjClass(projClass);
       tempro.setProjName(projName);
       tempro.setProjProvince(projProvince);
       tempro.setProjRegion(projRegion);
       getProjFacade().create(tempro);
        return "center.xhtml";//注册成功之后跳转到个人中心
    }

    public User getTempUser() {
        return tempUser;
    }

    public void setTempUser(User tempUser) {
        this.tempUser = tempUser;
    }

    public Project getTempro() {
        return tempro;
    }

    public void setTempro(Project tempro) {
        this.tempro = tempro;
    }

//----------------------------------------------------------------------------sxy-----------------------------------------------------------------------------
    
    //当前请求详情的项目，按投资从多到少排列的第n项
      public Donate getDonate(int n){  
        List<Donate>  donate= new ArrayList<Donate>();
            donate=this.investFacade.getDonate(detail.getProjId());
            Donate d=donate.get(n-1);
        return d;
    }
    //读入用户和项目，判断是否守护
    public boolean isGuard(){
        GuardProj guard=this.guardFacade.getGuard(current,detail);
        if(guard==null)
            return false;
        else return true;
    }

    //通过判断是否守护取得心形标志的状态
   public String imageState(){
        //用户未登录调取登陆页面，心心空的  
        if(this.current==null){
            return "heart_e.png";
        }
        else {
           GuardProj guard= this.guardFacade.getGuard(current,detail);
            if(guard.getGuardProjPK().getUserTel()=="defult"){//查询守护没有结果返回defult对象
                return "heart_e.png";
            } 
            else{
                return "heart_f.png";
            }
        }
    }
   //守护项目（写数据库）
   public String changeState(){
       if(current==null)
           return "login.xhtml";
       else{
           if(this.guardFacade.getGuard(current,detail).getGuardProjPK().getUserTel()!="defult")//当前是守护状态
               this.guardFacade.deleteGuard(current, detail);
           else
               this.guardFacade.guardProj(current, detail);
           return null;
       }
  
   }

    public Project getDetail(){
        return detail;
    }
    public User getUser(){
       //测试数据
        return current;
    }

//-------------------------------------------------------------------------wqm----------------------------------------------------------------------------
    
    //当前用户申请项目总数
    public int getMyProjectSum(){
       int sum = getProjFacade().countByUserTel(current.getUserTel());        
       return sum;
    }
    
    //当前用户守护项目总数
    public int getMyGuardSum(){
        int sum = getGuardFacade().countByUserTel(current.getUserTel());        
        return sum;
    }
    
    //当前用户资助项目总数
    public int getMyInvestSum(){
        int sum = getInvestFacade().countByUserTel(current.getUserTel());        
        return sum;
    }
    
    //当前用户守护的项目
    public List<Project> getMyGurad(){
        List<GuardProj> myGuard = getGuardFacade().findByUserTel(current.getUserTel());
        List<Integer> all_proj_id = new ArrayList<>();
        Iterator<GuardProj> iter = myGuard.iterator();
        while(iter.hasNext()){
            int temp = (Integer) iter.next().getGuardProjPK().getProjId();
            all_proj_id.add(temp);
        }
        List<Project> all_proj = new ArrayList<>();
        for(int i = 0; i < all_proj_id.size(); i++){
            int temp = all_proj_id.get(i);
            Project proj = getProjFacade().findByProjId(temp);
            all_proj.add(proj);
        }
        return all_proj;
    }
    
    //当前用户资助过的项目
    public List<Project> getMyInvest(){
       List<InvesteProj> myInvest = getInvestFacade().findByUserTel(current.getUserTel());
       List<Integer> all_proj_id = new ArrayList<>();
       Iterator<InvesteProj> iter = myInvest.iterator();
       while(iter.hasNext()){
            int temp = (Integer) iter.next().getInvesteProjPK().getProjId();
            all_proj_id.add(temp);
       }
       List<Project> all_proj = new ArrayList<>();
       for(int i = 0; i < all_proj_id.size(); i++){
            int temp = all_proj_id.get(i);
            Project proj = getProjFacade().findByProjId(temp);
            all_proj.add(proj);
       }
       return all_proj;
    }
    
    //当前用户申请过的项目
    public List<Project> getMyProject(){
        System.out.print("我来了！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！!!!!!!!!!");
        List<Project> myProject = getProjFacade().findByUserTel(current.getUserTel());
        System.out.print("我实现了！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！!!!!!");
        return myProject;
    }
    
    //--------------------------------------------------------------------zyh------------------------------------------------------------------------------
    
    
    public String getKind() {
        return kind;
    }

    public void setKind(String kind_) {
        kind = kind_;
    }
    public String getProv() {
        return prov;
    }

    public void setProv(String prov_) {
        prov = prov_;
    }

    public List< SelectItem> getCataItems() {

        this.cataItems = new LinkedList< SelectItem>();
        this.cataItems.add(new SelectItem("", "所有"));
        this.cataItems.add(new SelectItem("雕塑工艺", "雕塑工艺"));
        this.cataItems.add(new SelectItem("陶瓷制作工艺", "陶瓷制作工艺"));
        this.cataItems.add(new SelectItem("织染工艺", "织染工艺"));
        this.cataItems.add(new SelectItem("传统建筑营造工艺", "传统建筑营造工艺"));
        this.cataItems.add(new SelectItem("金属冶炼加工工艺", "金属冶炼加工工艺"));
        this.cataItems.add(new SelectItem("工具器械制作工艺", "工具器械制作工艺"));
        this.cataItems.add(new SelectItem("文房四宝制作工艺", "文房四宝制作工艺"));
        this.cataItems.add(new SelectItem("印刷术工艺", "印刷术工艺"));
        this.cataItems.add(new SelectItem("刻绘工艺", "刻绘工艺"));
        this.cataItems.add(new SelectItem("髹漆工艺", "髹漆工艺"));
        this.cataItems.add(new SelectItem("家具制作工艺", "家具制作工艺"));
        this.cataItems.add(new SelectItem("编制扎制工艺", "编制扎制工艺"));
        this.cataItems.add(new SelectItem("特种工艺及其他", "特种工艺及其他"));
        return cataItems;
    }

    public List< SelectItem> getProvItems() {

        this.provItems = new LinkedList< SelectItem>();

        this.provItems.add(new SelectItem("", "所有"));
        this.provItems.add(new SelectItem("北京市", "北京市"));
        this.provItems.add(new SelectItem("天津市", "天津市"));
        this.provItems.add(new SelectItem("河北省", "河北省"));
        this.provItems.add(new SelectItem("天津市", "天津市"));
        this.provItems.add(new SelectItem("山西省", "山西省"));
        this.provItems.add(new SelectItem("内蒙古自治区", "内蒙古自治区"));
        this.provItems.add(new SelectItem("辽宁省", "辽宁省"));
        this.provItems.add(new SelectItem("黑龙江省", "黑龙江省"));
        this.provItems.add(new SelectItem("上海市", "上海市"));
        this.provItems.add(new SelectItem("江苏省", "江苏省"));
        this.provItems.add(new SelectItem("浙江省", "浙江省"));
        this.provItems.add(new SelectItem("安徽省", "安徽省"));
        this.provItems.add(new SelectItem("福建省", "福建省"));
        this.provItems.add(new SelectItem("江西省", "江西省"));
        this.provItems.add(new SelectItem("山东省", "山东省"));
        this.provItems.add(new SelectItem("河南省", "河南省"));
        this.provItems.add(new SelectItem("湖北省", "湖北省"));
        this.provItems.add(new SelectItem("湖南省", "湖南省"));
        this.provItems.add(new SelectItem("广东省", "广东省"));
        this.provItems.add(new SelectItem("广西壮族自治区", "广西壮族自治区"));
        this.provItems.add(new SelectItem("海南省", "海南省"));
        this.provItems.add(new SelectItem("重庆市", "重庆市"));
        this.provItems.add(new SelectItem("四川省", "四川省"));
        this.provItems.add(new SelectItem("贵州省", "贵州省"));
        this.provItems.add(new SelectItem("云南省", "云南省"));
        this.provItems.add(new SelectItem("西藏自治区", "西藏自治区"));
        this.provItems.add(new SelectItem("陕西省", "陕西省"));
        this.provItems.add(new SelectItem("甘肃省", "甘肃省"));
        this.provItems.add(new SelectItem("青海省", "青海省"));
        this.provItems.add(new SelectItem("宁夏回族自治区", "宁夏回族自治区"));
        this.provItems.add(new SelectItem("新疆维吾尔自治区", "新疆维吾尔自治区"));
        this.provItems.add(new SelectItem("新疆生产建设兵团", "新疆生产建设兵团"));
        this.provItems.add(new SelectItem("香港", "香港"));
        this.provItems.add(new SelectItem("澳门", "澳门"));
        this.provItems.add(new SelectItem("中直单位", "中直单位"));
        return provItems;
    }
    
    private ProjectFacade getProjFacade() {
        return projFacade;
    }
    
    private InvesteProjFacade getInvestFacade() {
        return investFacade;
    }
    
    private UserFacade getUserFacade() {
        return userFacade;
    }
    
    private GuardProjFacade getGuardFacade() {
        return guardFacade;
    }
/*tangkexin*/        
//get/set函数
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }
    

    public String getUserPsw() {
        return userPsw;
    }


    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }
public String getApplicantIDcard() {
        return applicantIDcard;
    }

    public void setApplicantIDcard(String applicantIDcard) {
        this.applicantIDcard = applicantIDcard;
    }

    public String getApplicantMail() {
        return applicantMail;
    }

    public void setApplicantMail(String applicantMail) {
        this.applicantMail = applicantMail;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getProjAddr() {
        return projAddr;
    }

    public void setProjAddr(String projAddr) {
        this.projAddr = projAddr;
    }

    public void setProjCatelog(String projCatelog) {
        this.projCatelog = projCatelog;
    }

    public String getProjCatelog() {
        return projCatelog;
    }

    public void setProjCity(String projCity) {
        this.projCity = projCity;
    }

    public String getProjCity() {
        return projCity;
    }

    public String getProjClass() {
        return projClass;
    }

    public void setProjClass(String projClass) {
        this.projClass = projClass;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjProvince() {
        return projProvince;
    }

    public void setProjProvince(String projProvince) {
        this.projProvince = projProvince;
    }

    public void setProjRegion(String projRegion) {
        this.projRegion = projRegion;
    }

    public String getProjRegion() {
        return projRegion;
    }

}
