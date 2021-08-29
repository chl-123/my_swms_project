package com.fs.swms.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.business.dto.*;
import com.fs.swms.business.entity.ApprovalSheet;
import com.fs.swms.business.entity.ProblemHandle;
import com.fs.swms.business.mapper.ProblemHandleMapper;
import com.fs.swms.business.service.IApprovalSheetService;
import com.fs.swms.business.service.IProblemHandleService;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.common.util.Utils;
import com.fs.swms.mainData.entity.ProblemType;
import com.fs.swms.mainData.service.IProblemTypeService;
import com.fs.swms.security.entity.OrganizationUser;
import com.fs.swms.security.entity.User;
import com.fs.swms.security.entity.UserRole;
import com.fs.swms.security.service.IOrganizationUserService;
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
 * @since 2021-08-23
 */
@Service
public class ProblemHandleServiceImpl extends ServiceImpl<ProblemHandleMapper, ProblemHandle> implements IProblemHandleService {
    @Autowired
    ProblemHandleMapper problemHandleMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IApprovalSheetService iApprovalSheetService;
    @Autowired
    private IOrganizationUserService iOrganizationUserService;
    @Autowired
            private IUserRoleService iUserRoleService;
    @Autowired
            private IRoleService iRoleService;
    @Autowired
            private IProblemTypeService iProblemTypeService;

    String dataPath="ProblemHandle/";
    @Override
    public Page<ProblemHandleInfo> selectProblemHandleList(Page<ProblemHandleInfo> page, QueryProblemHandle problemHandle,User user) {
        Page<ProblemHandleInfo> problemHandlePage=null;
        Subject subject = SecurityUtils.getSubject();
        QueryWrapper<OrganizationUser> organizationUserQueryWrapper=new QueryWrapper<>();
        organizationUserQueryWrapper.eq("user_id",user.getId());
        List<OrganizationUser> list = iOrganizationUserService.list(organizationUserQueryWrapper);
        if (list == null) {
            throw new BusinessException("当前用户查不到所属部门");
        }
        List<String> rolekey=new ArrayList<>();
        rolekey.add("FWKKZ");rolekey.add("GYKKZ");rolekey.add("SCKKZ");
        rolekey.add("ZGKKZ");rolekey.add("JSKKZ");rolekey.add("FWKKZ");
        rolekey.add("CWKKZ");rolekey.add("YXKKZ");
        QueryWrapper<UserRole> userRoleQueryWrapper=new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id",user.getId());
        List<UserRole> userRoleList = iUserRoleService.list(userRoleQueryWrapper);
        if (userRoleList == null) {
            throw new BusinessException("当前用户查不到角色ID");
        }
        boolean isrole=false;
        for(String role:rolekey){
            if (subject.hasRole(role)) {
                isrole = true;
                break;
            }
        }
        if (isrole) {
            problemHandle.setSetp("2");
            problemHandle.setChildSetp1("5");
            problemHandle.setDeptNo(list.get(0).getId());
            problemHandlePage = problemHandleMapper.selectProblemHandleForManagementList(page, problemHandle);
        }else {
            problemHandle.setChildSetp("1");
            problemHandle.setExecutor(user.getId());

            problemHandlePage = problemHandleMapper.selectProblemHandleForManagementList(page, problemHandle);
        }

        return problemHandlePage;
    }



    @Override
    public boolean updateProblemHandle(UpdateProblemHandle problemHandle,List<MultipartFile> files) {
        if (
                problemHandle.getId()==null||problemHandle.getApprovalSheetId()==null||
                        problemHandle.getProblemId()==null||problemHandle.getProcessScheme()==null
        ) {
            throw new BusinessException("必要数据不能为空");
        }
        ProblemHandle problemHandleInfo = this.getById(problemHandle.getId());
        if (problemHandleInfo == null) {
            throw new BusinessException("该问题处理表不存在");
        }
        UpdateWrapper<ProblemHandle> updateWrapper=new UpdateWrapper<>();

        ProblemHandle problemHandleEntity=new ProblemHandle();
        BeanUtils.copyProperties(problemHandle,problemHandleEntity);
        boolean result=false;
        String fileList=problemHandleInfo.getFilenames();

        String registerBaseFiles = problemHandle.getBaseFiles();
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
                problemHandleEntity.setFilenames(fileNames);
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
                    problemHandleEntity.setFilenames(fileNames);
                }
            }
        }else{
            if (files.size()!=0&&files!=null){
                String fileNames="";
                String fileName =Utils.uploadFiles(files, dataPath);
                fileNames=fileName+fileNames;
                problemHandleEntity.setFilenames(fileNames);
            }else {
                updateWrapper.set("FILENAMES","");
            }
        }
        updateWrapper.eq("ID",problemHandle.getId());
        result = this.update(problemHandleEntity, updateWrapper);
        return result;
    }


    @Override
    public ProblemHandleInfoEntity selectProblemHandleById(String id) {
        ProblemHandle problemHandle = problemHandleMapper.selectById(id);
        ProblemHandleInfoEntity problemHandleInfoEntity=new ProblemHandleInfoEntity();
        BeanUtils.copyProperties(problemHandle,problemHandleInfoEntity);
        List<ProblemType> problemTypeList=new ArrayList<>();
        String problemids=problemHandle.getProblemId();
        if (problemids!=null) {
            String[] problemIdList = problemids.split(";");
            for (String problemId:problemIdList){
                ProblemType problemType = iProblemTypeService.getById(problemId);
                if (problemType != null) {
                    problemTypeList.add(problemType);
                }
            }
            problemHandleInfoEntity.setProblemList(problemTypeList);

        }

        return problemHandleInfoEntity;
    }

    @Override
    public boolean transfer(CreateProblemHandle problemHandle, User user) {
        boolean result=false;
        ProblemHandle problemHandleEntity=new ProblemHandle();
        if (problemHandle.getApprovalSheetId()==null||problemHandle.getExecutor()==null||problemHandle.getId()==null) {
            throw new BusinessException("数据不能为空");
        }
        User executorUser = iUserService.getById(problemHandle.getExecutor());
        if (executorUser == null) {
            throw new BusinessException("该转办人在系统中不存在");
        }
        ApprovalSheet approvalSheet = iApprovalSheetService.getById(problemHandle.getApprovalSheetId());
        if (approvalSheet == null) {
            throw new BusinessException("该审批单在系统中不存在");
        }
        ProblemHandle byId = this.getById(problemHandle.getId());
        if (byId == null) {
            throw new BusinessException("该ID在系统中查询不到相应信息");
        }
        BeanUtils.copyProperties(problemHandle,problemHandleEntity);
        problemHandleEntity.setChildSetp("1");
        UpdateWrapper<ProblemHandle> problemHandleUpdateWrapper=new UpdateWrapper<>();
        problemHandleUpdateWrapper.eq("ID",problemHandle.getId());
        problemHandleUpdateWrapper.set("CREATOR",user.getId());
        problemHandleUpdateWrapper.set("CREATE_TIME",new Date());
        result= this.update(problemHandleEntity,problemHandleUpdateWrapper);

        UpdateWrapper<ApprovalSheet> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("SETP","3");
        updateWrapper.eq("ID",problemHandle.getApprovalSheetId());
        updateWrapper.set("OPERATOR",user.getId());
        updateWrapper.set("UPDATE_TIME",new Date());
        result=iApprovalSheetService.update(updateWrapper);

        return result;
    }

    @Override
    public boolean handle(CreateProblemHandle problemHandle, List<MultipartFile> files) {
        if (
                problemHandle.getId()==null||problemHandle.getApprovalSheetId()==null||
                problemHandle.getProblemId()==null||problemHandle.getProcessScheme()==null
        ) {
            throw new BusinessException("必要数据不能为空");
        }
        boolean result=false;

        ProblemHandle problemHandleEntity=new ProblemHandle();
        BeanUtils.copyProperties(problemHandle,problemHandleEntity);
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
            problemHandleEntity.setFilenames(fileNames);
        }
        problemHandleEntity.setChildSetp("2");
        UpdateWrapper<ProblemHandle> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("ID",problemHandle.getId());
        result=this.update(problemHandleEntity,updateWrapper);
        return result;
    }


    @Override
    public boolean cancel(String problemHandleId, String approvalSheetId,User user) {
        boolean result=false;
        if (problemHandleId == null||problemHandleId.equals("")||approvalSheetId==null||problemHandleId.equals("")) {
            throw new BusinessException("审批单表ID或问题处理表ID不能为空");
        }
        UpdateWrapper<ProblemHandle> updateWrapper=new UpdateWrapper<>();
        ProblemHandle problemHandle = this.getById(problemHandleId);
        if (problemHandle == null) {
            throw new BusinessException("该问题处理表不存在");
        }else {
            if (problemHandle.getFilenames()!=null) {
                String[] fileNameList = problemHandle.getFilenames().split(";");
                for (String fileName:fileNameList){//加判断
                    result = Utils.delFile(fileName);
                }
                updateWrapper.set("FILENAMES","");
            }
        }

        updateWrapper.set("CHILD_SETP","1");
        updateWrapper.set("PROBLEM_ID","");
        updateWrapper.set("PROCESS_SCHEME","");
        updateWrapper.set("OPERATOR",user.getId());
        updateWrapper.set("UPDATE_TIME",new Date());
        updateWrapper.eq("ID", problemHandleId);
        result = this.update(updateWrapper);
        return result;
    }
    @Override
    public Page<ProblemHandleInfo> selectProblemHandleListForManagement(Page<ProblemHandleInfo> page, QueryProblemHandle problemHandle,User user) {
        problemHandle.setOperator(user.getId());
        problemHandle.setChildSetp("2");
        List<ProblemType> problemTypeList=new ArrayList<>();
        Page<ProblemHandleInfo> problemHandleInfoPage = problemHandleMapper.selectProblemHandleForManagementList(page, problemHandle);
        List<ProblemHandleInfo> problemHandleList = problemHandleInfoPage.getRecords();
        for (ProblemHandleInfo problemHandInfo:problemHandleList) {
            String problemids=problemHandInfo.getProblemIds();
            if (problemids!=null) {
                String[] fileNameList = problemids.split(";");
                for (String id:fileNameList){
                    ProblemType problemType = iProblemTypeService.getById(id);
                    if (problemType != null) {
                        problemTypeList.add(problemType);
                    }
                }

            }

            problemHandInfo.setProblemList(problemTypeList);
        }
        return problemHandleInfoPage;
    }
}
