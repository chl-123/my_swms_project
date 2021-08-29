package com.fs.swms.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.business.dto.*;
import com.fs.swms.business.entity.ApprovalSheet;
import com.fs.swms.business.entity.ProblemHandle;
import com.fs.swms.business.entity.ServiceRegister;
import com.fs.swms.business.mapper.ProblemHandleMapper;
import com.fs.swms.business.mapper.ServiceRegisterMapper;
import com.fs.swms.business.service.IApprovalSheetService;
import com.fs.swms.business.service.IProblemHandleService;
import com.fs.swms.business.service.IServiceRegisterService;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.common.util.Utils;
import com.fs.swms.mainData.entity.ProblemType;
import com.fs.swms.mainData.service.IProblemTypeService;
import com.fs.swms.security.entity.User;
import com.fs.swms.security.service.IRoleService;
import com.fs.swms.security.service.IUserRoleService;
import com.fs.swms.security.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chl
 * @since 2021-08-24
 */
@Service
public class ServiceRegisterServiceImpl extends ServiceImpl<ServiceRegisterMapper, ServiceRegister> implements IServiceRegisterService {
    @Autowired
    private IProblemTypeService problemTypeService;
    @Autowired
    private IApprovalSheetService iApprovalSheetService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IUserRoleService iUserRoleService;
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private ServiceRegisterMapper serviceRegisterMapper;
    @Autowired
            private ProblemHandleMapper problemHandleMapper;
    String dataPath="ServiceRegister/";
    @Autowired
    private IProblemHandleService iProblemHandleService;
    @Override
    public boolean createServiceRegister(CreateServiceRegister serviceRegister, List<MultipartFile> files) {
        if (
                serviceRegister.getServiceNo() == null || serviceRegister.getServiceType() == null ||
                serviceRegister.getServiceLevel() == null||serviceRegister.getProblemId()==null||
                        serviceRegister.getIsShangxiu()==null||serviceRegister.getId()==null
        ) {
            throw new BusinessException("必要数据不能为空");
        }
        boolean result=false;
        QueryWrapper<ServiceRegister> serviceRegisterQueryWrapper=new QueryWrapper<>();
        serviceRegisterQueryWrapper.eq("SERVICE_NO",serviceRegister.getServiceNo());
        List<ServiceRegister> serviceRegisterList = this.list(serviceRegisterQueryWrapper);
        if (!CollectionUtils.isEmpty(serviceRegisterList)) {
            throw new BusinessException("该服务编号已存在");
        }

        ProblemType problemType = problemTypeService.getById(serviceRegister.getProblemId());
        if (problemType == null) {
            throw new BusinessException("问题类型不存在");
        }
        ServiceRegister serviceRegisterEntity=new ServiceRegister();
        BeanUtils.copyProperties(serviceRegister,serviceRegisterEntity);
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
            serviceRegisterEntity.setFilenames(fileNames);
        }
        serviceRegisterEntity.setChildSetp("2");
        UpdateWrapper<ServiceRegister> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("ID",serviceRegister.getId());
        result=this.update(serviceRegisterEntity,updateWrapper);
        return result;
    }



    public boolean updateServiceRegister(UpdateServiceRegister serviceRegister, List<MultipartFile> files) {
        if (
                serviceRegister.getServiceNo() == null || serviceRegister.getServiceType() == null ||
                serviceRegister.getServiceLevel() == null||serviceRegister.getProblemId()==null||
                serviceRegister.getIsShangxiu()==null||serviceRegister.getId()==null
        ) {
            throw new BusinessException("必要数据不能为空");
        }
        ServiceRegister serviceRegisterInfo = this.getById(serviceRegister.getId());

        if (serviceRegisterInfo == null) {
            throw new BusinessException("查询不到该信息");
        }
        boolean result=false;
        QueryWrapper<ServiceRegister> serviceRegisterQueryWrapper=new QueryWrapper<>();
        serviceRegisterQueryWrapper.ne("ID",serviceRegister.getId()).and(e->e.eq("SERVICE_NO",serviceRegister.getServiceNo()));
        List<ServiceRegister> serviceRegisterList = this.list(serviceRegisterQueryWrapper);
        if (!CollectionUtils.isEmpty(serviceRegisterList)) {
            throw new BusinessException("该服务编号已存在");
        }

        ProblemType problemType = problemTypeService.getById(serviceRegister.getProblemId());
        if (problemType == null) {
            throw new BusinessException("问题类型不存在");
        }


        ServiceRegister serviceRegisterEntity=new ServiceRegister();
        BeanUtils.copyProperties(serviceRegister,serviceRegisterEntity);

        UpdateWrapper<ServiceRegister> updateWrapper=new UpdateWrapper<>();


        String fileList=serviceRegisterInfo.getFilenames();

        String registerBaseFiles = serviceRegister.getBaseFiles();
        Map<String,Integer> fileMap=new HashMap<>();
        if (registerBaseFiles!=null&&!registerBaseFiles.equals("")) {
            String[] productFilesList=registerBaseFiles.split(";");
            for (int i = 0; i < productFilesList.length; i++) {
                fileMap.put(productFilesList[i],1);
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
                serviceRegisterEntity.setFilenames(fileNames);
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
                    serviceRegisterEntity.setFilenames(fileNames);
                }
            }
        }else{
            if (files.size()!=0&&files!=null){
                String fileNames="";
                String fileName =Utils.uploadFiles(files, dataPath);
                fileNames=fileName+fileNames;
                serviceRegisterEntity.setFilenames(fileNames);
            }else {
                updateWrapper.set("FILENAMES","");
            }
        }
        /*if (serviceRegisterInfo.getApprovalStatus().equals("2")) {
            updateWrapper.set("APPROVAER","");
//            updateWrapper.set("APPROVAL_RESULT","0");
            updateWrapper.set("APPROVAL_COMMENTS","");
            updateWrapper.set("APPROVAL_DATE","");
            updateWrapper.set("APPROVAL_STATUS","0");
        }
        if (serviceRegisterInfo.getLeaderApprovalStatus().equals("2")) {
            updateWrapper.set("LEADER_APPROVAL","");
//            updateWrapper.set("APPROVAL_RESULT","0");
            updateWrapper.set("LEADER_APPROVAL_COMMENTS","");
            updateWrapper.set("LEADER_APPROVAL_DATE","");
            updateWrapper.set("LEADER_APPROVAL_STATUS","0");
        }*/
        serviceRegisterEntity.setChildSetp("2");
        updateWrapper.eq("ID",serviceRegister.getId());

        result =this.update(serviceRegisterEntity,updateWrapper);
        return result;
    }



    @Override
    public Page<ServiceRegisterInfo> selectRegisterList(Page<ServiceRegisterInfo> page, QueryServiceRegister serviceRegister,User user) {
        Subject subject = SecurityUtils.getSubject();
        boolean hasRole = subject.hasRole("FWKKZ");
        if (hasRole) {
            serviceRegister.setApprovalStatus("1");
            serviceRegister.setSetp("1");
        }else {
            serviceRegister.setChildSetp("1");
            serviceRegister.setExecutor(user.getId());
        }
        Page<ServiceRegisterInfo> serviceRegisterInfoPage = serviceRegisterMapper.selectServiceRegisterList(page, serviceRegister);

        return serviceRegisterInfoPage;
    }

    @Override
    public boolean deleteServiceRegister(String approvalSheetId, String serviceRegisterId,User user) {
        boolean result=false;
        ApprovalSheet approvalSheet = iApprovalSheetService.getById(approvalSheetId);
        if (approvalSheet==null) {
            throw new BusinessException("该审批表不存在");
        }
        ServiceRegister serviceRegister = this.getById(serviceRegisterId);
        if (serviceRegister == null) {
            throw new BusinessException("该服务登记表不存在");
        }

        UpdateWrapper<ServiceRegister> registerUpdateWrapper=new UpdateWrapper<>();
        registerUpdateWrapper.eq("ID",serviceRegisterId);
        registerUpdateWrapper.set("UPDATE_TIME",new Date());
        registerUpdateWrapper.set("OPERATOR",user.getId());
        registerUpdateWrapper.set("DEL_FLAG","1");
        if (serviceRegister.getFilenames()!=null) {
            String[] fileNameList = serviceRegister.getFilenames().split(";");
            for (String fileName:fileNameList){//加判断
                result = Utils.delFile(fileName);
            }
            registerUpdateWrapper.set("FILENAMES","");
        }
        result = this.update(registerUpdateWrapper);
        UpdateWrapper<ApprovalSheet> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("SETP","1");
        updateWrapper.set("UPDATE_TIME",new Date());
        updateWrapper.eq("ID",approvalSheetId);
        result=iApprovalSheetService.update(updateWrapper);
        return result;
    }

    @Override
    public ServiceRegisterInfo selectServiceRegisterById(String id) {
        if (id == null) {
            throw new BusinessException("ID不能为空");
        }
        ServiceRegister serviceRegister = this.getById(id);
        if (serviceRegister == null) {
            throw new BusinessException("查询不到该信息");
        }
        ServiceRegisterInfo serviceRegisterInfo=new ServiceRegisterInfo();
        BeanUtils.copyProperties(serviceRegister,serviceRegisterInfo);
        if (serviceRegister.getCreator()!=null) {
            User user = iUserService.getById(serviceRegister.getCreator());
            serviceRegisterInfo.setCreatorName(user.getUserName());
        }
        if (serviceRegister.getOperator()!=null) {
            User user = iUserService.getById(serviceRegister.getOperator());
            serviceRegisterInfo.setOperatorName(user.getUserName());
        }
        if (serviceRegister.getApprovaer()!=null) {
            User user = iUserService.getById(serviceRegister.getApprovaer());
            serviceRegisterInfo.setApprovaerName(user.getUserName());
        }
        if (serviceRegister.getLeaderApprovaer()!=null) {
            User user = iUserService.getById(serviceRegister.getLeaderApprovaer());
            serviceRegisterInfo.setLeaderApprovaerName(user.getUserName());
        }
        if (serviceRegister.getProblemId()!=null) {
            ProblemType problemType = problemTypeService.getById(serviceRegister.getProblemId());
            serviceRegisterInfo.setTypeName(problemType.getTypeName());
        }


        return serviceRegisterInfo;
    }


    @Override
    public Page<ServiceRegisterInfo> selectRegListForManagement(Page<ServiceRegisterInfo> page, QueryServiceRegisterManagement serviceRegister, User user) {
        if (serviceRegister.getChildSetp()==null) {
            serviceRegister.setChildSetp("2");
        }
        serviceRegister.setExecutor(user.getId());
        Page<ServiceRegisterInfo> serviceRegisterInfoPage = serviceRegisterMapper.selectRegisterListForManagement(page, serviceRegister);
        return serviceRegisterInfoPage;
    }




    @Override
    public boolean transfer(ServiceRegisterTransfer transfer, User user) {
        boolean result=false;
        ServiceRegister serviceRegister=new ServiceRegister();
        if (transfer.getApprovalSheetId()==null||transfer.getExecutor()==null) {
            throw new BusinessException("数据不能为空");
        }
        User executorUser = iUserService.getById(transfer.getExecutor());
        if (executorUser == null) {
            throw new BusinessException("该转办人在系统中不存在");
        }
        ApprovalSheet approvalSheet = iApprovalSheetService.getById(transfer.getApprovalSheetId());
        if (approvalSheet == null) {
            throw new BusinessException("该审批单在系统中不存在");
        }
        BeanUtils.copyProperties(transfer,serviceRegister);
        serviceRegister.setChildSetp("1");
        serviceRegister.setOperator(user.getId());
        serviceRegister.setUpdateTime(new Date());
        result= this.save(serviceRegister);

        UpdateWrapper<ApprovalSheet> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("SETP","2");
        updateWrapper.eq("ID",transfer.getApprovalSheetId());
        updateWrapper.set("UPDATE_TIME",new Date());
        updateWrapper.set("OPERATOR",user.getId());
        result=iApprovalSheetService.update(updateWrapper);

        return result;
    }
    @Override
    public boolean Approval(UpdateServiceRegister serviceRegister, User user) {

        if (serviceRegister.getId()==null) {
            throw new BusinessException("当前ID不能为空");
        }
        ServiceRegister serviceRegisterGetById = this.getById(serviceRegister.getId());
        if (serviceRegisterGetById == null) {
            throw new BusinessException("查询不到该服务登记表");
        }
        Subject subject = SecurityUtils.getSubject();
        UpdateWrapper<ServiceRegister> updateWrapper =new UpdateWrapper<>();
        if (subject.hasRole("FWKKZ")) {
            if (
                serviceRegister.getApprovalComments()==null||serviceRegister.getApprovalComments().equals("") ||
                serviceRegister.getApprovalStatus()==null||serviceRegister.getApprovalStatus().equals("")
            ) {
                throw new BusinessException("审批意见或审批结果不能为空");
            }
            updateWrapper.set("APPROVAER",user.getId());
            updateWrapper.set("APPROVAL_COMMENTS",serviceRegister.getApprovalComments());
            updateWrapper.set("APPROVAL_DATE",new Date());
            updateWrapper.set("APPROVAL_STATUS",serviceRegister.getApprovalStatus());
            updateWrapper.set("CHILD_SETP","3");
            updateWrapper.set("UPDATE_TIME",new Date());
            updateWrapper.set("OPERATOR",user.getId());


        }
        else if (subject.hasRole("FWKLD")) {
            if (serviceRegister.getLeaderApprovalStatus()==null||serviceRegister.getLeaderApprovalComments().equals("")
                    ||serviceRegister.getLeaderApprovalComments()==null||serviceRegister.getLeaderApprovalStatus().equals("")) {
                throw new BusinessException("审批意见或审批结果不能为空");
            }

            updateWrapper.set("LEADER_APPROVAER",user.getId());
            updateWrapper.set("LEADER_APPROVAL_COMMENTS",serviceRegister.getLeaderApprovalComments());
            updateWrapper.set("LEADER_APPROVAL_DATE",new Date());
            updateWrapper.set("LEADER_APPROVAL_STATUS",serviceRegister.getLeaderApprovalStatus());
            updateWrapper.set("CHILD_SETP","4");
            updateWrapper.set("UPDATE_TIME",new Date());
            updateWrapper.set("OPERATOR",user.getId());
        }
        else {
            throw new BusinessException("您没有权限审批");
        }
        updateWrapper.eq("ID",serviceRegister.getId());
        boolean result = this.update( updateWrapper);
        return result;
    }

    @Override
    public boolean cancelApproval(String serviceRegisterId, User user) {
        if (serviceRegisterId==null) {
            throw new BusinessException("当前ID不能为空");
        }
        ServiceRegister serviceRegister = this.getById(serviceRegisterId);
        if (serviceRegister == null) {
            throw new BusinessException("查询不到该服务登记表");
        }
        Subject subject = SecurityUtils.getSubject();
        UpdateWrapper<ServiceRegister> updateWrapper =new UpdateWrapper<>();
        if (subject.hasRole("FWKKZ")) {
            if("3".equals(serviceRegister.getChildSetp())) {
                updateWrapper.set("APPROVAER", "");
                updateWrapper.set("APPROVAL_COMMENTS", "");
                updateWrapper.set("APPROVAL_DATE", "");
                updateWrapper.set("APPROVAL_STATUS", "0");
                updateWrapper.set("CHILD_SETP", "2");
                updateWrapper.set("UPDATE_TIME", new Date());
                updateWrapper.set("OPERATOR", user.getId());
            }else {
                throw new BusinessException("无法取消审批");
            }
        }
        else if (subject.hasRole("FWKLD")) {
            if("4".equals(serviceRegister.getChildSetp())) {
                updateWrapper.set("LEADER_APPROVAER", "");
                updateWrapper.set("LEADER_APPROVAL_COMMENTS", "");
                updateWrapper.set("LEADER_APPROVAL_DATE", "");
                updateWrapper.set("LEADER_APPROVAL_STATUS", "0");
                updateWrapper.set("CHILD_SETP", "3");
                updateWrapper.set("UPDATE_TIME", new Date());
                updateWrapper.set("OPERATOR", user.getId());
            }else {
                throw new BusinessException("无法取消审批");
            }
        }
        else {
            throw new BusinessException("您没有权限取消审批");
        }
        updateWrapper.eq("ID",serviceRegisterId);
        boolean result = this.update( updateWrapper);
        return result;
    }
    @Override
    public boolean distribution(Distribution problemHandles, User user) {
        boolean result=false;
        if (CollectionUtils.isEmpty(problemHandles.getDeptNoList())) {
            throw new BusinessException("责任部门不能为空");
        }
        if (problemHandles.getApprovalSheetId()==null) {
            throw new BusinessException("审批单ID不能为空");
        }
        ApprovalSheet approvalSheet = iApprovalSheetService.getById(problemHandles.getApprovalSheetId());
        if (approvalSheet == null) {
            throw new BusinessException("该审批单不存在");
        }
        List<ProblemHandle> problemHandleList=new ArrayList<>();
        for(String depNo :problemHandles.getDeptNoList()){
            ProblemHandle problemHandle=new ProblemHandle();
            problemHandle.setApprovalSheetId(problemHandles.getApprovalSheetId());
            problemHandle.setDeptNo(depNo);
            problemHandle.setChildSetp("0");
            problemHandle.setUpdateTime(new Date());
            problemHandle.setOperator(user.getId());
            problemHandleList.add(problemHandle);
        }
        result= iProblemHandleService.saveBatch(problemHandleList);
        UpdateWrapper<ServiceRegister> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("CHILD_SETP","5");
        updateWrapper.eq("APPROVAL_SHEET_ID",problemHandles.getApprovalSheetId());
        updateWrapper.set("UPDATE_TIME",new Date());
        updateWrapper.set("OPERATOR",user.getId());
        result=this.update(updateWrapper);
        return result;
    }
    public boolean cancelDistribution(String approvalSheetId,User user){
        if (approvalSheetId == null||approvalSheetId.equals("")) {
            throw new BusinessException("审批单ID不能为空");

        }
        boolean result=false;
        QueryWrapper<ProblemHandle> queryWrapper=new QueryWrapper<>();
        queryWrapper.ne("CHILD_SETP","0").eq("APPROVAL_SHEET_ID",approvalSheetId);
        List<ProblemHandle> problemHandleList = iProblemHandleService.list(queryWrapper);
        if (!CollectionUtils.isEmpty(problemHandleList)) {
            throw new BusinessException("下发部门已对问题进行处理，不能取消分发操作!");
        }
        UpdateWrapper<ProblemHandle> problemHandleUpdateWrapper=new UpdateWrapper<>();
        problemHandleUpdateWrapper.eq("APPROVAL_SHEET_ID",approvalSheetId);
        problemHandleUpdateWrapper.set("DEL_FLAG","1");
        problemHandleUpdateWrapper.set("UPDATE_TIME",new Date());
        problemHandleUpdateWrapper.set("OPERATOR",user.getId());
        result=iProblemHandleService.update(problemHandleUpdateWrapper);
        UpdateWrapper<ServiceRegister> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("CHILD_SETP","4");
        updateWrapper.eq("APPROVAL_SHEET_ID",approvalSheetId);
        updateWrapper.set("UPDATE_TIME",new Date());
        updateWrapper.set("OPERATOR",user.getId());
        result=this.update(updateWrapper);
        return result;

    }

    @Override
    public boolean deleteProblemHandle(String approvalSheetId, String problemHandleId, User user) {
        boolean result=false;
        QueryWrapper<ProblemHandle> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("APPROVAL_SHEET_ID",approvalSheetId);
        List<ProblemHandle> problemHandleList = iProblemHandleService.list(queryWrapper);

        UpdateWrapper<ProblemHandle> problemHandleUpdateWrapper=new UpdateWrapper<>();
        problemHandleUpdateWrapper.eq("ID",problemHandleId);
        problemHandleUpdateWrapper.set("DEL_FLAG","1");
        problemHandleUpdateWrapper.set("UPDATE_TIME",new Date());
        problemHandleUpdateWrapper.set("OPERATOR",user.getId());
        result=iProblemHandleService.update(problemHandleUpdateWrapper);

        if (problemHandleList.size()==1) {
            UpdateWrapper<ServiceRegister> updateWrapper=new UpdateWrapper<>();
            updateWrapper.set("CHILD_SETP","4");
            updateWrapper.eq("APPROVAL_SHEET_ID",approvalSheetId);
            updateWrapper.set("UPDATE_TIME",new Date());
            updateWrapper.set("OPERATOR",user.getId());
            result=this.update(updateWrapper);
        }
        return result;
    }

    @Override
    public Page<ServiceRegisterInfo> selectRegListForApproval(Page<ServiceRegisterInfo> page, QueryServiceRegisterManagement serviceRegister) {

        Page<ServiceRegisterInfo> serviceRegisterInfoPage = serviceRegisterMapper.selectRegisterListForManagement(page, serviceRegister);
        return serviceRegisterInfoPage;

    }

    @Override
    public Page<ServiceRegisterInfo> selectRegListForDistribution(Page<ServiceRegisterInfo> page, QueryServiceRegisterManagement serviceRegister) {
        if (serviceRegister.getChildSetp()==null) {
            serviceRegister.setChildSetp1("4");
            serviceRegister.setChildSetp2("5");
        }
        Page<ServiceRegisterInfo> serviceRegisterInfoPage = serviceRegisterMapper.selectListForDistribution(page, serviceRegister);

        return serviceRegisterInfoPage;
    }
    @Override
    public Page<ProblemHandleInfo> selectRegListForDistributionManagement(Page<ProblemHandleInfo> page, QueryProblemHandle problemHandle) {
        if (problemHandle.getChildSetp()==null) {
            problemHandle.setChildSetp("0");
        }
        Page<ProblemHandleInfo> serviceRegisterInfoPage = problemHandleMapper.selectProblemHandleForManagementList(page,problemHandle);

        return serviceRegisterInfoPage;
    }

}
