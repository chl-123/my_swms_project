package com.fs.swms.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.business.dto.ApprovalSheetInfo;
import com.fs.swms.business.dto.CreateApprovalSheet;
import com.fs.swms.business.dto.QueryApprovalSheet;
import com.fs.swms.business.dto.UpdateApprovalSheet;
import com.fs.swms.business.entity.ApprovalSheet;
import com.fs.swms.business.entity.ServiceRegister;
import com.fs.swms.business.mapper.ApprovalSheetMapper;
import com.fs.swms.business.service.IApprovalSheetService;
import com.fs.swms.business.service.IServiceRegisterService;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.common.util.Utils;
import com.fs.swms.mainData.entity.Customer;
import com.fs.swms.mainData.entity.Product;
import com.fs.swms.mainData.entity.Windfarm;
import com.fs.swms.mainData.service.ICustomerService;
import com.fs.swms.mainData.service.IProductService;
import com.fs.swms.mainData.service.IWindfarmService;
import com.fs.swms.security.entity.User;
import com.fs.swms.security.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chl
 * @since 2021-08-18
 */
@Service
public class ApprovalSheetServiceImpl extends ServiceImpl<ApprovalSheetMapper, ApprovalSheet> implements IApprovalSheetService {
    @Autowired
    ApprovalSheetMapper approvalSheetMapper;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IWindfarmService iWindfarmService;
    @Autowired
    private ICustomerService iCustomerService;
    @Autowired
    private IUserService iUserService;

    @Lazy
    @Autowired
    private IServiceRegisterService iServiceRegisterService;

    String dataPath="ApprovalSheet/";
    @Override
    public boolean createApprovalSheet(CreateApprovalSheet approvalSheet, List<MultipartFile> files) {

        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        //查找相应的条件
        if (
                (approvalSheet.getBoxNo()==null||approvalSheet.getBoxNo().equals(""))||
                (approvalSheet.getAddress()==null||approvalSheet.getAddress().equals(""))||
                (approvalSheet.getContacts()==null||approvalSheet.getContacts().equals(""))||
                (approvalSheet.getTel()==null||approvalSheet.getTel().equals(""))||
                (approvalSheet.getSegments()==null||approvalSheet.getSegments().equals(""))||
                (approvalSheet.getIsFree()==null||approvalSheet.getIsFree().equals(""))||
                (approvalSheet.getProblemDesc()==null||approvalSheet.getProblemDesc().equals(""))
        ) {
            throw new BusinessException("必要数据不能为空");
        }
        productQueryWrapper.eq("BOX_NO", approvalSheet.getBoxNo());
        List<Product> productList = iProductService.list(productQueryWrapper);
        if (CollectionUtils.isEmpty(productList)) {
            throw new BusinessException("齿轮箱号在系统中不存在");
        }

        //判断隶属板块是否合法
        if (!(approvalSheet.getSegments().equals("2") || approvalSheet.getSegments().equals("1"))) {
            throw new BusinessException("隶属板块号无效");
        } else if (!(approvalSheet.getIsFree().equals("0") || approvalSheet.getIsFree().equals("1"))) {
            throw new BusinessException("是否无偿服务号无效");//判断无偿服务是否合法
        }
        //拷贝数据
        ApprovalSheet approvalSheetEntity = new ApprovalSheet();
        BeanUtils.copyProperties(approvalSheet, approvalSheetEntity);
        approvalSheetEntity.setProductId(productList.get(0).getId());
        approvalSheetEntity.setSetp("1");
        String fileNames="";
        if (files.size()!=0&&files!=null) {
            for (MultipartFile file :files){
                if (!file.getOriginalFilename().equals("")&&file!=null&&file.getOriginalFilename()!=null) {
                    MyFile myFile=new MyFile();
                    myFile.setFile(file);
                    String fileName = Utils.uploadFile(dataPath,myFile);
                    fileNames=dataPath+fileName+";"+fileNames;
                }
            }
            approvalSheetEntity.setFilenames(fileNames);
        }
        //执行保存
        boolean result = this.save(approvalSheetEntity);
        return result;
    }


    @Override
    public Page<ApprovalSheetInfo> selectApprovalSheetList(Page<ApprovalSheetInfo> page, QueryApprovalSheet approvalSheet,User user) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("SYSADMIN")) {
            approvalSheet.setCreator(user.getId());
        }
        Page<ApprovalSheetInfo> approvalSheetPage = approvalSheetMapper.selectApprovalSheetList(page, approvalSheet);
        return approvalSheetPage;
    }

    @Override
    public Page<ApprovalSheetInfo> selectApprovalSheetAll(Page<ApprovalSheetInfo> page) {
        QueryApprovalSheet approvalSheet=new QueryApprovalSheet();
        Page<ApprovalSheetInfo> approvalSheetPage = approvalSheetMapper.selectApprovalSheetList(page, approvalSheet);
        return approvalSheetPage;
    }


    @Override
    public boolean deleteApprovalSheet(String id) {
        boolean result = this.removeById(id);
        return result;
    }

    @Override
    public boolean updateApprovalSheet(UpdateApprovalSheet approvalSheet, List<MultipartFile> files) {
        if (
                (approvalSheet.getBoxNo()==null||approvalSheet.getBoxNo().equals(""))||
                (approvalSheet.getAddress()==null||approvalSheet.getAddress().equals(""))||
                (approvalSheet.getContacts()==null||approvalSheet.getContacts().equals(""))||
                (approvalSheet.getTel()==null||approvalSheet.getTel().equals(""))||
                (approvalSheet.getSegments()==null||approvalSheet.getSegments().equals(""))||
                (approvalSheet.getIsFree()==null||approvalSheet.getIsFree().equals(""))||
                (approvalSheet.getProblemDesc()==null||approvalSheet.getProblemDesc().equals(""))||
                (approvalSheet.getId()==null||approvalSheet.getId().equals(""))

        ) {
            throw new BusinessException("必要数据不能为空");
        }
        boolean result=false;
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        //查找相应的条件
        productQueryWrapper.eq("BOX_NO", approvalSheet.getBoxNo());
        List<Product> productList = iProductService.list(productQueryWrapper);
        if (CollectionUtils.isEmpty(productList)) {
            throw new BusinessException("齿轮箱号在系统中不存在");
        }
        ApprovalSheet approvalSheetEntity=new ApprovalSheet();
        BeanUtils.copyProperties(approvalSheet,approvalSheetEntity);
        approvalSheetEntity.setProductId(productList.get(0).getId());

        UpdateWrapper<ApprovalSheet> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",approvalSheet.getId());
        List<ApprovalSheet> list = this.list(updateWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("该审批单不存在");
        }
        String fileList=list.get(0).getFilenames();

        String approvalSheetFiles = approvalSheet.getBaseFiles();

        Map<String,Integer> fileMap=new HashMap<>();
        if (approvalSheetFiles!=null&&!approvalSheetFiles.equals("")) {
            String[] approvalSheetFilesList=approvalSheetFiles.split(";");
            for (int i = 0; i < approvalSheetFilesList.length; i++) {
                fileMap.put(approvalSheetFilesList[i],1);
            }
        }

        if (fileList!=null) {
            String[] fileNameList = fileList.split(";");
            String fileNames="";
            if (files.size()!=0&&files!=null) {
                if (fileNameList!= null&&fileNameList.length!=0) {
                    if (fileMap.size()==0) {
                        for (String fileName:fileNameList){//加判断
                            result = Utils.delFile(fileName);
                        }
                    }else {
                        for (String fileName:fileNameList){//加判断
                            if (!fileMap.containsKey(fileName)) {
                                result = Utils.delFile(fileName);
                            }else {
                                fileNames=fileName+";"+fileNames;
                            }
                        }

                    }
                }
                String fileName =Utils.uploadFiles(files, dataPath);
                fileNames=fileName+fileNames;
                approvalSheetEntity.setFilenames(fileNames);
            }else {
                if (fileMap.size()==0) {
                    for (String fileName:fileNameList){//加判断
                        result = Utils.delFile(fileName);
                    }
                    updateWrapper.set("FILENAMES","");
                }else {
                    for (String fileName:fileNameList){//加判断
                        if (!fileMap.containsKey(fileName)) {
                            result = Utils.delFile(fileName);
                        }else {
                            fileNames=fileName+";"+fileNames;
                        }
                    }
                    approvalSheetEntity.setFilenames(fileNames);
                }
            }
        }else{
            if (files.size()!=0&&files!=null){
                String fileNames="";
                String fileName =Utils.uploadFiles(files, dataPath);
                fileNames=fileName+fileNames;
                approvalSheetEntity.setFilenames(fileNames);
            }else {
                updateWrapper.set("FILENAMES","");
            }
        }

        if (list.get(0).getApprovalResult().equals("2")) {
            updateWrapper.set("APPROVAER","");
            updateWrapper.set("APPROVAL_RESULT","0");
            updateWrapper.set("APPROVAL_COMMENTS","");
            updateWrapper.set("APPROVAL_DATE","");
            updateWrapper.set("APPROVAL_STATUS","0");
        }
        //查询从数据库中中查数据
         result = this.update(approvalSheetEntity, updateWrapper);
        return result;
    }


    @Override
    public ApprovalSheetInfo selectApprovalSheetById(String approvalSheetId) {

        ApprovalSheet approvalSheet = this.getById(approvalSheetId);
        if (approvalSheet == null) {
            throw new BusinessException("根据当前ID查询不到审批单");
        }
        ApprovalSheetInfo approvalSheetInfo=new ApprovalSheetInfo();
        Product product = iProductService.getById(approvalSheet.getProductId());
        if (product == null) {
            throw new BusinessException("查询不到齿轮箱信息");
        }
        Customer customer = iCustomerService.getById(product.getCustomerId());
        if (customer == null) {
            throw new BusinessException("查询不到客户信息");
        }
        Windfarm windfarm = iWindfarmService.getById(product.getWindfarmId());
        if (windfarm == null) {
            throw new BusinessException("查询不到风场信息");
        }
        if (approvalSheet.getApprovaer()!=null) {
            User approverName = iUserService.getById(approvalSheet.getApprovaer());
            approvalSheetInfo.setApprovaerName(approverName.getUserName());
        }
        if (approvalSheet.getOperator()!=null) {
            User operatorName = iUserService.getById(approvalSheet.getOperator());
            approvalSheetInfo.setOperatorName(operatorName.getUserName());
        }

        BeanUtils.copyProperties(product,approvalSheetInfo);
        approvalSheetInfo.setCustomerName(customer.getCustomerName());
        approvalSheetInfo.setWindfarm(windfarm.getWindfarm());


        BeanUtils.copyProperties(approvalSheet,approvalSheetInfo);
        return approvalSheetInfo;
    }

    @Override
    public boolean Approval(UpdateApprovalSheet updateApprovalSheet, User user) {
        if (updateApprovalSheet.getApprovalComments()==null) {
            throw new BusinessException("审批意见不能为空");
        }
        QueryWrapper<ApprovalSheet> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("ID",updateApprovalSheet.getId());
        List<ApprovalSheet> list = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("根据当前ID查询不到审批单");
        }
        ApprovalSheet approvalSheet=new ApprovalSheet();
        BeanUtils.copyProperties(updateApprovalSheet,approvalSheet);
        approvalSheet.setApprovaer(user.getId());
        approvalSheet.setApprovalStatus("1");
//        approvalSheet.setApprovalResult("1");
//        approvalSheet.setOperator(user.getId());
        approvalSheet.setApprovalDate(new Date());
        approvalSheet.setUpdateTime(new Date());
        queryWrapper.eq("ID",updateApprovalSheet.getId());
        boolean result = this.update(approvalSheet, queryWrapper);
        return result;
    }
    public boolean cancelApproval(String approvalSheetId,User user) {
        if (approvalSheetId==null) {
            throw new BusinessException("当前ID不能为空");
        }
        QueryWrapper<ApprovalSheet> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("ID",approvalSheetId);
        List<ApprovalSheet> list = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("根据当前ID查询不到审批单");
        }else {
            for (ApprovalSheet approvalSheet:list){
                if (approvalSheet.getApprovalStatus().equals("0")) {
                    throw new BusinessException("当前审批单还未审批");
                }
            }
        }
        QueryWrapper<ServiceRegister> registerQueryWrapper=new QueryWrapper<>();
        registerQueryWrapper.eq("APPROVAL_SHEET_ID",approvalSheetId);
        List<ServiceRegister> registerList = iServiceRegisterService.list(registerQueryWrapper);
        if (!CollectionUtils.isEmpty(registerList)) {
            throw new BusinessException("此审批单服务科已受理，不能取消审批!");
        }
        UpdateWrapper<ApprovalSheet> updateWrapper =new UpdateWrapper<>();
        updateWrapper.set("APPROVAER","");
        updateWrapper.set("APPROVAL_RESULT","0");
        updateWrapper.set("APPROVAL_COMMENTS","");
        updateWrapper.set("APPROVAL_DATE","");
        updateWrapper.set("APPROVAL_STATUS","0");
        updateWrapper.eq("ID",approvalSheetId);
        boolean result = this.update( updateWrapper);
        return result;
    }


}
