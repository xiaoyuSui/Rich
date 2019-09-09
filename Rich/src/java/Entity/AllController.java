/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private Project detail = new Project();
    //登录数据
    private User tempUser;
    //注册数据
    private Project tempro;
    //种类
    private Integer kind = 0;
    //省份
    private Integer prov = 0;

    //下拉框
    //类别
    private List<SelectItem> cataItems = null;
    //地区
    private List<SelectItem> provItems = null;

    private int donation = 0;

    /*tangkexin*/
    //input绑定
    private String userName;
    private String userTel;
    private String userPsw;
    private String applicantName;
    private String applicantIDcard;
    private String applicantMail;
    private String applDate;
    private int projClass;
    private String projName;
    private int projProvince;
    private String projCity;
    private String projRegion;
    private String projAddr;
    private String appliantPhone;
    private String projDepict;
    
    

    public AllController() {
        System.out.print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
        //current = new User("13671021552", "wqm", "password");
        detail = new Project();
        String date = "2019.9.7";
        //测试数据
         detail.setProjName("脆皮鸭文学");
        detail.setProjId(1);
        detail.setProjClass(1);
        detail.setApplDate(date);
        detail.setProjProvince(1);
        detail.setApplicantName("隋小雨");
        detail.setApplicantMail("13611393447@163.com");
        detail.setProjAddr("北京邮电大学");
        detail.setProjDepict("布洛陀是壮族先民口头文学中的神话人物，是创世神、始祖神和道德神。《布洛陀》是壮族的长篇诗体创世神话，主要记述布洛陀开天辟地、创造人类的丰功伟绩，自古以来以口头方式在广西壮族自治区田阳县一带传承。大约从明代起，在口头传唱的同时，也以古壮字书写的形式保存下来，其中有一部分变成壮族民间麽教的经文。\n"
                + "　　《布洛陀》的内容包括布洛陀创造天地、造人、造万物、造土皇帝、造文字历书和造伦理道德六个方面，反映了人类从茹毛饮血的蒙昧时代走向农耕时代的历史，以及壮族先民氏族部落社会的情况，在历史学、文学、宗教学、古文字学、音韵学和音乐学研究等方面有一定的学术价值。\n"
                + "　　布洛陀口传诗体创世神话在内容上具有原生性特点，在漫长的口头传承过程中，经过一代代的不断加工和锤炼，艺术性也得到了完善和提高。它不仅可以帮助人们认识历史、满足人们的生活需求，还具有教化的作用。\n"
           + "　　由于历史及其他各种原因，今天《布洛陀》已面临失传的危机，需要采取普查、建档、研究、出版等手段，并通过建立布洛陀文化生态保护村、唱诵队、传习馆以及在相关学校开办传习班等方式加以保护，使其能在现代化社会条件下继续得到传承。");
         
    }

//--------------------------------------------------------------------tkx----------------------------------------------------------------------------------
    public String forget() {
        User u;
        u = getUserFacade().checklogup(userTel);
        if (u != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("你的密码为" + u.getUserPsw()));
            return "log_in";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("该手机号未注册"));
            return "log_up";
        }
    }

    public String updatePsw() {
        User u;
        u = getUserFacade().checklogup(userTel);
        if (u != null) {
            u.setUserPsw(userPsw);
            getUserFacade().edit(u);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("你的密码已改为" + userPsw));
            return "log_in";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("该手机号未注册"));
            return "log_up";
        }
    }

   public String logUp(){
        tempUser = new User();
        tempUser.setUserName(userName);
        tempUser.setUserPsw(userPsw);
        tempUser.setUserTel(userTel);
        if(getUserFacade().checklogup(userTel) != null){
            //弹出手机号已注册，转到登录界面
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
        ("该手机号已注册"));
        }else{
        getUserFacade().create(tempUser);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
        ("注册成功，请登录"));
        }
        return "log_in"; //注册成功返回登陆界面
    }
   
    public String logIn(){
        current = new User();
        User u = getUserFacade().checklogup(userTel);
        if(userTel.equals("00000000000")&&userPsw.equals("000000")){
            return "manager.xhtml";   //跳转至管理员界面
        }
        else if(getUserFacade().checkLogin(userTel, userPsw)==null){
            if(u == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
        ("该手机号未注册"));
                return "log_up.xhtml";  //手机号未注册，转至注册页面
            }
            else{
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("密码错误"));
            return "log_in.xhtml";  //密码错误
                    }
        }
        System.out.print(u.getUserName());
        
        current.setUserName(u.getUserName());
        current.setUserPsw(u.getUserPsw());
        current.setUserTel(u.getUserTel());
        return "index.xhtml"; //登陆成功返回首页
    }
    
public String creartPro() {
        tempro = new Project();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String d = df.format(new Date());
        System.out.println(tempro.getProjId());
        User u =getUserFacade().checklogup("13671021552");
        tempro.setApplDate("qqq");
        System.out.println(u.getUserName());
        tempro.setUserTel(u);
        tempro.setApplicantIDcard("1234567890123456789");
        tempro.setApplicantMail("qqq");
        tempro.setProjAddr("qqq");
        tempro.setApplicantName("Qqq");
        tempro.setProjCity("qqq");
        tempro.setProjClass(1);
        tempro.setProjName("qqq");
        tempro.setProjProvince(1);
        tempro.setProjRegion("qqq");
        tempro.setProjId(999);
         System.out.println(tempro.getApplDate());
         System.out.println(tempro.getApplicantIDcard());
        System.out.println(tempro.getApplicantMail());
        System.out.println(tempro.getApplicantName());
         System.out.println(tempro.getProjAddr());
          System.out.println(tempro.getProjCity());
           System.out.println(tempro.getProjClass());
            System.out.println(tempro.getProjProvince());
             System.out.println(tempro.getProjRegion());
             // System.out.println(tempro.getUserTel().getUserTel());
            
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
    //详情页对项目捐款
    public String Donate() {
        if (current == null) {
            return "log_in.xhtml";
        } else {
            this.investFacade.investeProj(current, detail, donation);
            return null;
        }
    }

    //通过判断是否守护取得心形标志的状态
    public String guardState(int n) {//n=1设置字符串
        String state = "empty";
        //用户未登录调取登陆页面，心心空的  
        if (this.current == null) {
            state = "empty";
        } else {
            GuardProj guard = this.guardFacade.getGuard(current, detail);
            if (guard.getGuardProjPK().getUserTel() == "defult") {//查询守护没有结果返回defult对象
                state = "empty";
            } else {
                state = "filled";
            }
        }
        if (n == 1) {
            if (state == "empty") {
                return "守护项目";
            } else {
                return "取消守护";
            }
        } else {
            if (state == "empty") {
                return "heart_e.png";
            } else {
                return "heart_f.png";
            }
        }

    }

    public int getDonation() {
        return this.donation;
    }

    public void setDonation(int num) {
        this.donation = num;
    }

    public boolean isLogin() {
        if (current == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isLogout() {
        if (current == null) {
            return true;
        } else {
            return false;
        }
    }

    //当前请求详情的项目，按投资从多到少排列的第n项
    public Donate getDonate(int n) {
        List<Donate> donate = new ArrayList<Donate>();
        donate = this.investFacade.getDonate(detail.getProjId());
        Donate d = donate.get(n - 1);
        return d;
    }

    //读入用户和项目，判断是否守护
    public boolean isGuard() {
        GuardProj guard = this.guardFacade.getGuard(current, detail);
        if (guard == null) {
            return false;
        } else {
            return true;
        }
    }

    //通过判断是否守护取得心形标志的状态
    public String imageState() {
        //用户未登录调取登陆页面，心心空的  
        if (this.current == null) {
            return "heart_e.png";
        } else {
            GuardProj guard = this.guardFacade.getGuard(current, detail);
            if (guard.getGuardProjPK().getUserTel() == "defult") {//查询守护没有结果返回defult对象
                return "heart_e.png";
            } else {
                return "heart_f.png";
            }
        }
    }
    //守护项目（写数据库）

    public String changeState() {
        if (current == null) {
            return "log_in.xhtml";
        } else {
            if (this.guardFacade.getGuard(current, detail).getGuardProjPK().getUserTel() != "defult")//当前是守护状态
            {
                this.guardFacade.deleteGuard(current, detail);
            } else {
                this.guardFacade.guardProj(current, detail);
            }
            return null;
        }

    }

    public Project getDetail() {
        return detail;
    }

    public User getUser() {
        //测试数据
        return current;
    }

//-------------------------------------------------------------------------wqm----------------------------------------------------------------------------
    //当前用户申请项目总数
    public int getMyProjectSum() {
        int sum = getProjFacade().countByUserTel(current.getUserTel());
        return sum;
    }

    //当前用户守护项目总数
    public int getMyGuardSum() {
        int sum = getGuardFacade().countByUserTel(current.getUserTel());
        return sum;
    }

    //当前用户资助项目总数
    public int getMyInvestSum() {
        int sum = getInvestFacade().countByUserTel(current.getUserTel());
        return sum;
    }

    //当前用户守护的项目
    public List<Project> getMyGurad() {
        List<GuardProj> myGuard = getGuardFacade().findByUserTel(current.getUserTel());
        List<Integer> all_proj_id = new ArrayList<>();
        Iterator<GuardProj> iter = myGuard.iterator();
        while (iter.hasNext()) {
            int temp = (Integer) iter.next().getGuardProjPK().getProjId();
            all_proj_id.add(temp);
        }
        List<Project> all_proj = new ArrayList<>();
        for (int i = 0; i < all_proj_id.size(); i++) {
            int temp = all_proj_id.get(i);
            Project proj = getProjFacade().findByProjId(temp);
            all_proj.add(proj);
        }
        return all_proj;
    }

    //当前用户资助过的项目
    public List<Project> getMyInvest() {
        List<InvesteProj> myInvest = getInvestFacade().findByUserTel(current.getUserTel());
        List<Integer> all_proj_id = new ArrayList<>();
        Iterator<InvesteProj> iter = myInvest.iterator();
        while (iter.hasNext()) {
            int temp = (Integer) iter.next().getInvesteProjPK().getProjId();
            all_proj_id.add(temp);
        }
        List<Project> all_proj = new ArrayList<>();
        for (int i = 0; i < all_proj_id.size(); i++) {
            int temp = all_proj_id.get(i);
            Project proj = getProjFacade().findByProjId(temp);
            all_proj.add(proj);
        }
        return all_proj;
    }

    //当前用户申请过的项目
    public List<Project> getMyProject() {      
        List<Project> myProject = getProjFacade().findByUserTel(current.getUserTel());
        return myProject;
    }

    //获取当前捐款的金额
    public double getFund(int id) {
        double fund = getInvestFacade().findByUserTelAndProjId(current.getUserTel(), id).getFunding();
        return fund;
    }

    //--------------------------------------------------------------------zyh------------------------------------------------------------------------------
    public String toProj(Project pro) {
        detail = pro;
        kind = 0;
        prov = 0;
        return "detail";
    }
    public String indexChangeKind() {
        kind = 0;
        prov = 0;
        return "index";
    }

    public String institutionChangeKind() {
        kind = 0;
        prov = 0;
        return "institution";
    }

    public String listChangeKind() {
        kind = 0;
        prov = 0;
        return "list";
    }

    public String introduceChangeKind() {
        kind = 0;
        prov = 0;
        return "introduce";
    }

    public String ziyuanChangeKind() {
        kind = 0;
        prov = 0;
        return "ziyuan";
    }


    public String setMyKind(int k){
        kind = k;
        return "/list.xhtml";
    }
    
    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind_) {
        
        kind = kind_;
    }

    public Integer getProv() {
        return prov;
    }

    public void setProv(Integer prov_) {
        prov = prov_;
    }

    public String cataIntCnvtToString(Integer in) {
        System.out.print("啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊aaaaaaaaaaaaaaaaa");
        switch (in) {
            case 0:
                return "全部类别";
            case 1:
                return "雕塑工艺";
            case 2:
                return "陶瓷制作工艺";
            case 3:
                return "织染工艺";
            case 4:
                return "传统建筑营造工艺";
            case 5:
                return "金属冶炼加工工艺";
            case 6:
                return "工具器械制作工艺";
            case 7:
                return "文房四宝制作工艺";
            case 8:
                return "印刷术工艺";
            case 9:
                return "刻绘工艺";
            case 10:
                return "髹漆工艺";
            case 11:
                return "家具制作工艺";
            case 12:
                return "编制扎制工艺";
            case 13:
                return "特种工艺及其他";
            default:
                return "无";
        }
       // String class=in.getClass().getName();
       // System.out.print(in.getClass().getName());
    }

    public String provIntCnvtToString(Integer in) {
        switch (in) {
            case 0:
                return "全部地区";
            case 1:
                return "北京市";
            case 2:
                return "天津市";
            case 3:
                return "河北省";
            case 4:
                return "山西省";
            case 5:
                return "内蒙古自治区";
            case 6:
                return "吉林省";
            case 7:
                return "辽宁省";
            case 8:
                return "黑龙江省";
            case 9:
                return "上海市";
            case 10:
                return "江苏省";
            case 11:
                return "浙江省";
            case 12:
                return "安徽省";
            case 13:
                return "福建省";
            case 14:
                return "江西省";
            case 15:
                return "山东省";
            case 16:
                return "河南省";
            case 17:
                return "湖北省";
            case 18:
                return "湖南省";
            case 19:
                return "广东省";
            case 20:
                return "广西壮族自治区";
            case 21:
                return "海南省";
            case 22:
                return "重庆市";
            case 23:
                return "四川省";
            case 24:
                return "贵州省";
            case 25:
                return "云南省";
            case 26:
                return "西藏自治区";
            case 27:
                return "陕西省";
            case 28:
                return "甘肃省";
            case 29:
                return "青海省";
            case 30:
                return "宁夏回族自治区";
            case 31:
                return "新疆维吾尔自治区";
            case 32:
                return "新疆生产建设兵团";
            case 33:
                return "香港";
            case 34:
                return "澳门";
            case 35:
                return "台湾省";
            default:
                return "无";
        }
    }

    public List< SelectItem> getCataItems() {

        this.cataItems = new LinkedList< SelectItem>();
        this.cataItems.add(new SelectItem(0, "选择类别"));
        this.cataItems.add(new SelectItem(1, "雕塑工艺"));
        this.cataItems.add(new SelectItem(2, "陶瓷制作工艺"));
        this.cataItems.add(new SelectItem(3, "织染工艺"));
        this.cataItems.add(new SelectItem(4, "传统建筑营造工艺"));
        this.cataItems.add(new SelectItem(5, "金属冶炼加工工艺"));
        this.cataItems.add(new SelectItem(6, "工具器械制作工艺"));
        this.cataItems.add(new SelectItem(7, "文房四宝制作工艺"));
        this.cataItems.add(new SelectItem(8, "印刷术工艺"));
        this.cataItems.add(new SelectItem(9, "刻绘工艺"));
        this.cataItems.add(new SelectItem(10, "髹漆工艺"));
        this.cataItems.add(new SelectItem(11, "家具制作工艺"));
        this.cataItems.add(new SelectItem(12, "编制扎制工艺"));
        this.cataItems.add(new SelectItem(13, "特种工艺及其他"));
        return cataItems;
    }

    public List< SelectItem> getProvItems() {

        this.provItems = new LinkedList< SelectItem>();

        this.provItems.add(new SelectItem(0, "选择地区"));
        this.provItems.add(new SelectItem(1, "北京市"));
        this.provItems.add(new SelectItem(2, "天津市"));
        this.provItems.add(new SelectItem(3, "河北省"));
        this.provItems.add(new SelectItem(4, "山西省"));
        this.provItems.add(new SelectItem(5, "内蒙古自治区"));
        this.provItems.add(new SelectItem(6, "吉林省"));
        this.provItems.add(new SelectItem(7, "辽宁省"));
        this.provItems.add(new SelectItem(8, "黑龙江省"));
        this.provItems.add(new SelectItem(9, "上海市"));
        this.provItems.add(new SelectItem(10, "江苏省"));
        this.provItems.add(new SelectItem(11, "浙江省"));
        this.provItems.add(new SelectItem(12, "安徽省"));
        this.provItems.add(new SelectItem(13, "福建省"));
        this.provItems.add(new SelectItem(14, "江西省"));
        this.provItems.add(new SelectItem(15, "山东省"));
        this.provItems.add(new SelectItem(16, "河南省"));
        this.provItems.add(new SelectItem(17, "湖北省"));
        this.provItems.add(new SelectItem(18, "湖南省"));
        this.provItems.add(new SelectItem(19, "广东省"));
        this.provItems.add(new SelectItem(20, "广西壮族自治区"));
        this.provItems.add(new SelectItem(21, "海南省"));
        this.provItems.add(new SelectItem(22, "重庆市"));
        this.provItems.add(new SelectItem(23, "四川省"));
        this.provItems.add(new SelectItem(24, "贵州省"));
        this.provItems.add(new SelectItem(25, "云南省"));
        this.provItems.add(new SelectItem(26, "西藏自治区"));
        this.provItems.add(new SelectItem(27, "陕西省"));
        this.provItems.add(new SelectItem(28, "甘肃省"));
        this.provItems.add(new SelectItem(29, "青海省"));
        this.provItems.add(new SelectItem(30, "宁夏回族自治区"));
        this.provItems.add(new SelectItem(31, "新疆维吾尔自治区"));
        this.provItems.add(new SelectItem(32, "新疆生产建设兵团"));
        this.provItems.add(new SelectItem(33, "香港"));
        this.provItems.add(new SelectItem(34, "澳门"));
        this.provItems.add(new SelectItem(35, "台湾省"));
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

    public void setProjCity(String projCity) {
        this.projCity = projCity;
    }

    public String getProjCity() {
        return projCity;
    }

    public int getProjClass() {
        return projClass;
    }

    public void setProjClass(int projClass) {
        this.projClass = projClass;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public int getProjProvince() {
        return projProvince;
    }

    public void setProjProvince(int projProvince) {
        this.projProvince = projProvince;
    }

    public void setProjRegion(String projRegion) {
        this.projRegion = projRegion;
    }

    public String getProjRegion() {
        return projRegion;
    }

  
    public String getAppliantPhone() {
        return appliantPhone;
    }

    public void setAppliantPhone(String appliantPhone) {
        this.appliantPhone = appliantPhone;
    }

    public String getProjDepict() {
        return projDepict;
    }

    public void setProjDepict(String projDepict) {
        this.projDepict = projDepict;
    }


    public String getApplDate() {
        return applDate;
    }

    public void setApplDate(String applDate) {
        this.applDate = applDate;
    }


}
