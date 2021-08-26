package com.fs.swms.mainData.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.common.excel.ExcelUtil;
import com.fs.swms.common.util.Utils;
import com.fs.swms.mainData.dto.*;
import com.fs.swms.mainData.entity.Customer;
import com.fs.swms.mainData.entity.Product;
import com.fs.swms.mainData.entity.Windfarm;
import com.fs.swms.mainData.mapper.ProductMapper;
import com.fs.swms.mainData.service.ICustomerService;
import com.fs.swms.mainData.service.IProductService;
import com.fs.swms.mainData.service.IWindfarmService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chl
 * @since 2021-08-17
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ICustomerService iCustomerService;
    @Autowired
    private IWindfarmService iWindfarmService;
    public boolean createProduct(CreateProduct product, List<MultipartFile> files){
        String dataPath="Product/";
        QueryWrapper<Product> ew=new QueryWrapper<>();
        //查找相应的条件
        if (product.getFigureNo()==null||product.getCustomerId()==null||product.getBoxNo()==null) {
            throw new BusinessException("数据不能为空");
        }
        ew.eq("CUSTOMER_ID",product.getCustomerId())
                .and(e->e.eq("FIGURE_NO",product.getFigureNo())
                        .and(f->f.eq("BOX_NO",product.getBoxNo())));
        List<Product> productList=this.list(ew);
        if(!CollectionUtils.isEmpty(productList)){//判断是否重复
            throw new BusinessException("当前客户、产品图号与齿轮箱的组合已存在");
        }
        //拷贝数据
        Product productEntity=new Product();
        BeanUtils.copyProperties(product,productEntity);
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
            productEntity.setFiles(fileNames);
        }

        //执行保存
        boolean result = this.save(productEntity);
        return result;
    }

    @Override
    public boolean updateProductForQM(UpdateProduct product,List<MultipartFile> files) {
        String dataPath="Product/";
        boolean result=false;
        QueryWrapper<Product> ew=new QueryWrapper<>();
        //查找相应的条件
        if (product.getId()==null||product.getFigureNo()==null||product.getCustomerId()==null||product.getBoxNo()==null) {
            throw new BusinessException("数据不能为空");
        }
        ew.ne("ID",product.getId()).and(h->h.eq("CUSTOMER_ID",product.getCustomerId())
                .and(e->e.eq("FIGURE_NO",product.getFigureNo())
                        .and(f->f.eq("BOX_NO",product.getBoxNo()))));
        List<Product> productList=this.list(ew);
        if(!CollectionUtils.isEmpty(productList)){//判断是否重复
            throw new BusinessException("当前客户、产品图号与齿轮箱的组合已存在");
        }
        Product productEntity=new Product();
        BeanUtils.copyProperties(product,productEntity);
        UpdateWrapper<Product> queryWrapper=new UpdateWrapper();
        queryWrapper.eq("ID",product.getId());
        List<Product> list = this.list(queryWrapper);

        String fileList=list.get(0).getFiles();


        String productFiles = product.getBaseFiles();

        Map<String,Integer> fileMap=new HashMap<>();
        if (productFiles!=null&&!productFiles.equals("")) {
            String[] productFilesList=productFiles.split(";");
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
                productEntity.setFiles(fileNames);
            }else {
                if (fileMap.size()==0) {
                    for (String fileName:fileNameList){//加判断
                        result = Utils.delFile(fileName);
                    }
                    queryWrapper.set("FILES","");
                }else {
                    for (String fileName:fileNameList){//加判断
                        if (!fileMap.containsKey(fileName)) {
                            result = Utils.delFile(fileName);
                        }else {
                            fileNames=fileName+";"+fileNames;
                        }
                    }
                    productEntity.setFiles(fileNames);
                }
            }
        }else{
            if (files.size()!=0&&files!=null){
                String fileNames="";
                String fileName =Utils.uploadFiles(files, dataPath);
                fileNames=fileName+fileNames;
                productEntity.setFiles(fileNames);
            }else {
                queryWrapper.set("FILES","");
            }
        }

        result = this.update(productEntity, queryWrapper);

        return result;
    }
    @Override
    public boolean updateProductForMarketing(UpdateProduct product) {
        QueryWrapper<Product> ew=new QueryWrapper<>();
        //查找相应的条件
        if (product.getId()==null||product.getJobNo()==null||product.getContractNo()==null||product.getBoxNo()==null) {
            throw new BusinessException("数据不能为空");
        }
        ew.ne("ID",product.getId()).and(w->w.eq("JOB_NO",product.getJobNo())
                .and(e->e.eq("CONTRACT_NO",product.getContractNo())
                .and(f->f.eq("BOX_NO",product.getBoxNo()))));
        List<Product> productList=this.list(ew);
        if(!CollectionUtils.isEmpty(productList)){//判断是否重复
            throw new BusinessException("当前的合同号、工号和齿轮箱号组合在系统中已存在!");
        }
        Product productEntity=new Product();
        BeanUtils.copyProperties(product,productEntity);
        UpdateWrapper<Product> queryWrapper=new UpdateWrapper();
        queryWrapper.eq("ID",product.getId());
        boolean result = this.update(productEntity, queryWrapper);

        return result;
    }

    @Override
    public boolean deleteProduct(String id) {
        boolean result = this.removeById(id);
        return result;
    }

    @Override
    public boolean batchCreateForQM(MyFile file) throws Exception {
        if (file.getFile()==null) {
            throw new BusinessException("文件不能为空，请选择文件上传");
        }
        Map<String, Integer> map1 = new HashMap<>();
        List<ReadExcelProductQM> dataList = ExcelUtil.read(file, ReadExcelProductQM.class);
        for(ReadExcelProductQM info:dataList){
            if (info.getCustomerName()==null) {
                throw new BusinessException("请填写客户名称!");
            }
            if (info.getFigureNo()==null) {
                throw new BusinessException("请填写产品图号!");
            }
            if (info.getBoxNo()==null) {
                throw new BusinessException("请填写齿轮箱编号!");
            }
            if (info.getPower()==null) {
                throw new BusinessException("请填写功率!");
            }
            if (info.getSpeedRatio()==null) {
                throw new BusinessException("请填写速比!");
            }
            if (info.getAssemblyDate()==null) {
                throw new BusinessException("请填写装配日期!");
            }else if(!Utils.isValidDate(info.getAssemblyDate())){
                throw new BusinessException("装配日期的格式不正确，请按yyyy-MM-dd的格式填写日期!");
            }
            if (info.getManufactureDate()==null) {
                throw new BusinessException("请填写出厂日期!");
            }else if(!Utils.isValidDate(info.getManufactureDate())){
                throw new BusinessException("出厂日期的格式不正确，请按yyyy-MM-dd的格式填写日期!");
            }

            if ((map1.containsKey(info.getCustomerName()))&&map1.containsKey(info.getFigureNo())&&map1.containsKey(info.getBoxNo())) {
                throw new BusinessException("文档中客户名称"+info.getCustomerName()+"产品图号"+info.getFigureNo()+"齿轮箱编号"+info.getBoxNo()+"在文档中有重复");
            } else {
                map1.put(info.getCustomerName(), 1);
                map1.put(info.getFigureNo(), 1);
                map1.put(info.getBoxNo(), 1);
            }

        }
        for(ReadExcelProductQM info:dataList){
            List<Customer> customers = iCustomerService.selectCustomers(info.getCustomerName());
            if (customers.size()==0) {
                throw new BusinessException("客户名称"+info.getCustomerName()+"在系统中不存在");
            }
            QueryWrapper<Product> ew=new QueryWrapper<>();
            //查找相应的条件
            Customer customer=customers.get(0);
            ew.eq("CUSTOMER_ID",customer.getId())
                    .and(e->e.eq("FIGURE_NO",info.getFigureNo())
                            .and(f->f.eq("BOX_NO",info.getBoxNo())));
            List<Product> productList=this.list(ew);
            if(!CollectionUtils.isEmpty(productList)){//判断是否重复
                throw new BusinessException("当前客户、产品图号与齿轮箱的组合已存在");
            }
        }

        Collection<Product> productList=new ArrayList<>();
        for(ReadExcelProductQM info:dataList){
            Product product=new Product();
            BeanUtils.copyProperties(info,product);
            List<Customer> customers = iCustomerService.selectCustomers(info.getCustomerName());
            Customer customer = customers.get(0);
            product.setCustomerId(customer.getId());
            Date assemblyDate = Utils.parse(info.getAssemblyDate());
            Date manufactureDate = Utils.parse(info.getManufactureDate());
            product.setAssemblyDate(assemblyDate);
            product.setManufactureDate(manufactureDate);
            productList.add(product);
        }
        boolean result = this.saveBatch(productList);
        return result;
    }

    @Override
    public boolean batchCreateForMarketing(MyFile file) throws Exception {
        Map<String, Integer> map1 = new HashMap<>();
        List<ReadExcelProductM> dataList = ExcelUtil.read(file, ReadExcelProductM.class);
        for(ReadExcelProductM info:dataList){
            if (info.getCustomerName()==null) {
                throw new BusinessException("客户名称不能为空");
            }
            if (info.getFigureNo()==null) {
                throw new BusinessException("请填写产品图号!");
            }
            if (info.getBoxNo()==null) {
                throw new BusinessException("请填写齿轮箱编号!");
            }
            if (info.getContractNo()==null) {
                throw new BusinessException("请填写合同号!");
            }
            if (info.getJobNo()==null) {
                throw new BusinessException("请填写工号!");
            }
            if (info.getWindfarm()==null) {
                throw new BusinessException("请选择安装风场!");
            }
            if (info.getFanNo()==null) {
                throw new BusinessException("请填写风机编号!");
            }
            if (info.getConnDate()==null) {
                throw new BusinessException("请填写并网时间!");
            }else if(!Utils.isValidDate(info.getConnDate())){
                throw new BusinessException("装并网时间的格式不正确，请按yyyy-MM-dd的格式填写日期!");
            }
            if (info.getExpireDate()==null) {
                throw new BusinessException("请填写出质保日期!");
            }else if(!Utils.isValidDate(info.getExpireDate())){
                throw new BusinessException("出质保日期的格式不正确，请按yyyy-MM-dd的格式填写日期!");
            }

            if ((map1.containsKey(info.getCustomerName()))&&map1.containsKey(info.getFigureNo())&&map1.containsKey(info.getBoxNo())) {
                map1.clear();
                throw new BusinessException("文档中客户名称"+info.getCustomerName()+"产品图号"+info.getFigureNo()+"齿轮箱编号"+info.getBoxNo()+"在文档中有重复");
            } else {
                map1.put(info.getCustomerName(), 1);
                map1.put(info.getFigureNo(), 1);
                map1.put(info.getBoxNo(), 1);
            }

        }
        for(ReadExcelProductM info:dataList){
            List<Customer> customers = iCustomerService.selectCustomers(info.getCustomerName());
            if (customers.size()==0) {
                throw new BusinessException("客户名称"+info.getCustomerName()+"在系统中不存在");
            }
            QueryWrapper<Product> ew=new QueryWrapper<>();
            //查找相应的条件
            Customer customer=customers.get(0);
            ew.eq("CUSTOMER_ID",customer.getId())
                    .and(e->e.eq("FIGURE_NO",info.getFigureNo())
                            .and(f->f.eq("BOX_NO",info.getBoxNo())));
            List<Product> productList=this.list(ew);
            if(CollectionUtils.isEmpty(productList)){//判断是否重复
                throw new BusinessException("客户"+info.getCustomerName()+"产品图号"+info.getFigureNo()+"齿轮箱号"+info.getBoxNo()+"系统中不存在");

            }
            QueryWrapper<Windfarm> windfarmQueryWrapper=new QueryWrapper<>();
            windfarmQueryWrapper.eq("CUSTOMER_ID",customer.getId()).and(e->e.eq("WINDFARM",info.getWindfarm()));
            List<Windfarm> list = iWindfarmService.list(windfarmQueryWrapper);
            if (CollectionUtils.isEmpty(list)) {
                throw new BusinessException("客户"+info.getCustomerName()+"的风场"+info.getWindfarm()+"在系统中不存在");
            }
            ew.eq("JOB_NO",info.getJobNo())
                    .and(e->e.eq("CONTRACT_NO",info.getContractNo())
                            .and(f->f.eq("BOX_NO",info.getBoxNo())));
            List<Product> productList1=this.list(ew);
            if(!CollectionUtils.isEmpty(productList1)){//判断是否重复
                throw new BusinessException("合同"+info.getContractNo()+"工号"+info.getJobNo()+"齿轮箱号"+info.getBoxNo()+"组合在系统中已存在!");
            }
        }
        Collection<Product> productList=new ArrayList<>();
        for(ReadExcelProductM info:dataList){
            Product product=new Product();
            BeanUtils.copyProperties(info,product);
            List<Customer> customers = iCustomerService.selectCustomers(info.getCustomerName());
            QueryWrapper<Product> ew=new QueryWrapper<>();
            //查找相应的条件
            Customer customer=customers.get(0);
            ew.eq("CUSTOMER_ID",customer.getId())
                    .and(e->e.eq("FIGURE_NO",info.getFigureNo())
                            .and(f->f.eq("BOX_NO",info.getBoxNo())));
            List<Product> productList1=this.list(ew);
            Product product1 = productList1.get(0);
            product.setId(product1.getId());
            Date connDate = Utils.parse(info.getConnDate());
            Date expireDate = Utils.parse(info.getExpireDate());
            product.setConnDate(connDate);
            product.setExpireDate(expireDate);
            productList.add(product);
        }
        boolean result = this.updateBatchById(productList);
        return result;
    }

    @Override
    public ProductInfo selectProductByIdForQuality(String id) {
        Product product = this.getById(id);
        if(product==null){
            throw new BusinessException("查询不到信息");
        }
        QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
        customerQueryWrapper.eq("ID",product.getCustomerId());
        List<Customer> customerList = iCustomerService.list(customerQueryWrapper);
        if(CollectionUtils.isEmpty(customerList)){
            throw new BusinessException("查询不到该客户");
        }
        ProductInfo productInfo=new ProductInfo();
        BeanUtils.copyProperties(product,productInfo);

        productInfo.setCustomerName(customerList.get(0).getCustomerName());
        if(productInfo==null){
            throw new BusinessException("查询不到信息");
        }
        return productInfo;
    }

    @Override
    public ProductInfo selectProductByIdForMarketing(String id) throws ParseException {
        Product product = this.getById(id);
        if(product==null){
            throw new BusinessException("查询不到信息");
        }
        QueryWrapper<Windfarm> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("CUSTOMER_ID",product.getCustomerId());
        List<Windfarm> windfarmList = iWindfarmService.list(queryWrapper);
        if(CollectionUtils.isEmpty(windfarmList)){
            throw new BusinessException("该客户对应的风场信息为空");
        }
        QueryWrapper<Customer> queryCustomer=new QueryWrapper<>();
        queryCustomer.eq("ID",product.getCustomerId());
        List<Customer> list = iCustomerService.list(queryCustomer);
        if(CollectionUtils.isEmpty(list)){
            throw new BusinessException("查询不到该客户");
        }
        ProductInfo productInfo=new ProductInfo();
        BeanUtils.copyProperties(product,productInfo);

        productInfo.setWindfarm(windfarmList.get(0).getWindfarm());
        productInfo.setWindfarmId(windfarmList.get(0).getId());
        productInfo.setCustomerName(list.get(0).getCustomerName());
        return productInfo;
    }

    @Override
    public Page<ProductInfo> selectAll(Page<ProductInfo> page) {
        Page<ProductInfo> productInfoPage = productMapper.selectProductList(page,new QueryProduct());
        return productInfoPage;
    }

    @Override
    public Page<ProductInfo> selectList(QueryProduct product, Page<ProductInfo> page) {

        Page<ProductInfo> productInfoPage = productMapper.selectProductList(page, product);
        return productInfoPage;
    }

    public ProductInfo selectProductByBoxNo(String boxNo)  {

        if (boxNo == null||boxNo.equals("")) {
            throw new BusinessException("齿轮箱号不能为空");
        }
        QueryWrapper<Product> productQueryWrapper=new QueryWrapper<>();
        productQueryWrapper.eq("BOX_NO",boxNo);
        List<Product> productList = this.list(productQueryWrapper);
        if(CollectionUtils.isEmpty(productList)){
            throw new BusinessException("系统中无此齿轮箱编号!");
        }
        Product product=productList.get(0);
        QueryWrapper<Windfarm> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("ID",product.getWindfarmId());
        List<Windfarm> windfarmList = iWindfarmService.list(queryWrapper);
        if(CollectionUtils.isEmpty(windfarmList)){
            throw new BusinessException("该产品对应的风场信息为空");
        }
        QueryWrapper<Customer> queryCustomer=new QueryWrapper<>();
        queryCustomer.eq("ID",product.getCustomerId());
        List<Customer> list = iCustomerService.list(queryCustomer);
        if(CollectionUtils.isEmpty(list)){
            throw new BusinessException("查询不到该客户");
        }
        ProductInfo productInfo=new ProductInfo();
        BeanUtils.copyProperties(product,productInfo);
        productInfo.setWindfarm(windfarmList.get(0).getWindfarm());
        productInfo.setWindfarmId(windfarmList.get(0).getId());
        productInfo.setCustomerName(list.get(0).getCustomerName());
        return productInfo;
    }

}
