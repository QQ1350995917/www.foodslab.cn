package cn.foodslab.controller.file;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.interceptor.MenuInterceptor;
import cn.foodslab.interceptor.SessionInterceptor;
import cn.foodslab.service.file.FFile;
import cn.foodslab.service.file.FileServices;
import cn.foodslab.service.file.IFileServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-11-01 15:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class FileController extends Controller implements IFileController {
    private IFileServices iFileServices = new FileServices();

    @Override
    public void index() {

    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mTypeCover() {
        UploadFile file = this.getFile();
        String params = this.getPara("p");
        IResultSet iResultSet = new ResultSet();
        VFFile requestVFFileEntity = JSON.parseObject(params, VFFile.class);
        if (!requestVFFileEntity.checkCreateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVFFileEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            return;
        }

        String filePath = "/upload/" + file.getFileName();
//        boolean fileCopy = FileServices.fileCopy(file.getFile(), new File(filePath));
//        if (!fileCopy) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestVFFileEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            return;
//        }
        FFile fFile;
        if (requestVFFileEntity.getFileId() == null) {
            requestVFFileEntity.setFileId(UUID.randomUUID().toString());
            requestVFFileEntity.setPath(filePath);
            requestVFFileEntity.setType("image");
            requestVFFileEntity.setStatus(2);
            fFile = iFileServices.mCreate(requestVFFileEntity);
        } else {
            requestVFFileEntity.setPath(filePath);
            fFile = iFileServices.mUpdate(requestVFFileEntity);
        }

        if (fFile == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVFFileEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VFFile(fFile));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFFile.class, "fileId", "path", "type", "trunkId")));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mTypeDirectionImage() {
        UploadFile file = this.getFile();
        String filePath = "/upload/direction_" + file.getFileName();
        IResultSet iResultSet = new ResultSet();
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VFFile(filePath));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VFFile.class, "path")));
    }
}
