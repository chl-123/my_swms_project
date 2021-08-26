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
import com.fs.swms.security.entity.UserRole;
import com.fs.swms.security.service.IRoleService;
import com.fs.swms.security.service.IUserRoleService;
import com.fs.swms.security.service.IUserService;
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
        boolean result=false;
        QueryWrapper<ServiceRegister> serviceRegisterQueryWrapper=new QueryWrapper<>();
        serviceRegisterQueryWrapper.ne("ID",serviceRegister.getId()).eq("SERVICE_NO",serviceRegister.getServiceNo());
        List<ServiceRegister> serviceRegisterList = this.list(serviceRegisterQueryWrapper);
        if (!CollectionUtils.isEmpty(serviceRegisterList)) {
            throw new BusinessException("该服务编号已存在");
        }

        ProblemType problemType = problemTypeService.getById(serviceRegister.getProblemId());
        if (problemType == null) {
            throw new BusinessException("问题类型不存在");
        }


        ServiceRegister serviceRegisterEntity=new ServiceRegister();
        UpdateWrapper<ServiceRegister> updateWrapper=new UpdateWrapper<>();

        ServiceRegister serviceRegisterInfo = this.getById(serviceRegister.getId());
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
        if (serviceRegisterInfo.getApprovalStatus().equals("2")) {
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
        }
        serviceRegisterEntity.setChildSetp("2");
        updateWrapper.eq("ID",serviceRegister.getId());

        result =this.update(serviceRegisterEntity,updateWrapper);
        return result;
    }

    @Override
    public Page<ServiceRegister> selectServiceRegisterAll(Page<ServiceRegister> page) {
        return null;
    }

    @Override
    public Page<ServiceRegisterInfo> selectRegisterList(Page<ServiceRegisterInfo> page, QueryServiceRegister serviceRegister,User user) {
        QueryWrapper<UserRole> userRoleQueryWrapper=new QueryWrapper<>();
//        userRoleQueryWrapper.eq("");
        return null;
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
    public boolean Approval(UpdateServiceRegister updateServiceRegister, User user) {
        if (updateServiceRegister.getApprovalComments()==null) {
            throw new BusinessException("审批意见不能为空");
        }
        QueryWrapper<ServiceRegister> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("ID",updateServiceRegister.getId());
        List<ServiceRegister> list = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("根据当前ID查询不到服务登记表");
        }
        ServiceRegister serviceRegisterEntity=new ServiceRegister();
        BeanUtils.copyProperties(updateServiceRegister,serviceRegisterEntity);
        serviceRegisterEntity.setApprovaer(user.getId());
//        serviceRegisterEntity.setApprovalStatus("1");
//        approvalSheet.setApprovalResult("1");
        serviceRegisterEntity.setOperator(user.getId());
        serviceRegisterEntity.setApprovalDate(new Date());
        serviceRegisterEntity.setUpdateTime(new Date());
        queryWrapper.eq("ID",updateServiceRegister.getId());
        boolean result = this.update(serviceRegisterEntity, queryWrapper);
        return result;
    }

    @Override
    public boolean leaderApproval(UpdateServiceRegister updateServiceRegister, User user) {
        if (updateServiceRegister.getApprovalComments()==null) {
            throw new BusinessException("审批意见不能为空");
        }
        QueryWrapper<ServiceRegister> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("ID",updateServiceRegister.getId());
        List<ServiceRegister> list = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("根据当前ID查询不到服务登记表");
        }

        UpdateWrapper<ServiceRegister> updateWrapper=new UpdateWrapper<>();
        ServiceRegister serviceRegisterEntity=new ServiceRegister();
        BeanUtils.copyProperties(updateServiceRegister,serviceRegisterEntity);
        if (updateServiceRegister.getLeaderApprovalStatus().equals("2")) {
            updateWrapper.set("APPROVAER","");
//            updateWrapper.set("APPROVAL_RESULT","0");
            updateWrapper.set("APPROVAL_COMMENTS","");
            updateWrapper.set("APPROVAL_DATE","");
            updateWrapper.set("APPROVAL_STATUS","0");
        }
        serviceRegisterEntity.setLeaderApprovaer(user.getId());
//        serviceRegisterEntity.setApprovalStatus("1");
//        approvalSheet.setApprovalResult("1");
        serviceRegisterEntity.setOperator(user.getId());
        serviceRegisterEntity.setApprovalDate(new Date());
        serviceRegisterEntity.setUpdateTime(new Date());
        updateWrapper.eq("ID",updateServiceRegister.getId());
        boolean result = this.update(serviceRegisterEntity, queryWrapper);
        return result;
    }

    @Override
    public boolean distribution(List<CreateProblemHandle> problemHandles) {
        if (CollectionUtils.isEmpty(problemHandles)) {
            throw new BusinessException("责任部门不能为空");
        }
        Collection<ProblemHandle> problemHandleList=new ArrayList<>();
        for(CreateProblemHandle createProblemHandle:problemHandles){
            ProblemHandle problemHandle=new ProblemHandle();
            BeanUtils.copyProperties(createProblemHandle,problemHandle);
            problemHandle.setChildSetp("0");
            problemHandleList.add(problemHandle);
        }
        boolean result = iProblemHandleService.saveBatch(problemHandleList);

        return result;
    }



    @Override
    public boolean executor(ServiceRegisterExecutor executor,User user) {
        boolean result=false;
        ServiceRegister serviceRegister=new ServiceRegister();
        if (executor.getApprovalSheetId()==null||executor.getExecutor()==null) {
            throw new BusinessException("数据不能为空");
        }
        User executorUser = iUserService.getById(executor.getExecutor());
        if (executorUser == null) {
            throw new BusinessException("该转办人在系统中不存在");
        }
        ApprovalSheet approvalSheet = iApprovalSheetService.getById(executor.getApprovalSheetId());
        if (approvalSheet == null) {
            throw new BusinessException("该审批单在系统中不存在");
        }
        BeanUtils.copyProperties(executor,serviceRegister);
        serviceRegister.setChildSetp("1");
        serviceRegister.setOperator(user.getId());
        serviceRegister.setUpdateTime(new Date());
        result= this.save(serviceRegister);

        UpdateWrapper<ApprovalSheet> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("SETP","2");
        updateWrapper.eq("ID",executor.getApprovalSheetId());
        result=iApprovalSheetService.update(updateWrapper);

        return result;
    }
}
