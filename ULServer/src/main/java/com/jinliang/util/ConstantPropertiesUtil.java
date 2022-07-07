package com.jinliang.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * implements InitializingBean初始化bean的时候
 *
 * @author GMT
 * @date 2022/7/5
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {
    @Value("${tencent.cos.file.secretId}")
    private String secretId;

    @Value("${tencent.cos.file.secretKey}")
    private String secretKey;

    @Value("${tencent.cos.file.region}")
    private String region;

    @Value("${tencent.cos.file.bucketname}")
    private String bucketname;

    @Value("${tencent.cos.file.avatar_url}")
    private String avatarUrl;

    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    public static String AVATAR_URL;

    public static COSClient cosClient;


    @Override
    public void afterPropertiesSet() {
        END_POINT = region;
        ACCESS_KEY_ID = secretId;
        ACCESS_KEY_SECRET = secretKey;
        BUCKET_NAME = bucketname;
        AVATAR_URL = avatarUrl;
    }

    public static COSClient createCOSClient() {
        COSCredentials cred = new BasicCOSCredentials(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        Region region = new Region(END_POINT);
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        return new COSClient(cred, clientConfig);
    }

    // 创建 TransferManager 实例，这个实例用来后续调用高级接口
    public static TransferManager createTransferManager() {
        // 创建一个 COSClient 实例，这是访问 COS 服务的基础实例。
        // 详细代码参见本页: 简单操作 -> 创建 COSClient
        COSClient cosClient = createCOSClient();

        // 自定义线程池大小，建议在客户端与 COS 网络充足（例如使用腾讯云的 CVM，同地域上传 COS）的情况下，设置成16或32即可，可较充分的利用网络资源
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        // 传入一个 threadpool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosClient, threadPool);

        // 设置高级接口的配置项
        // 分块上传阈值和分块大小分别为 5MB 和 1MB
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
        transferManagerConfiguration.setMinimumUploadPartSize(1 * 1024 * 1024);
        transferManager.setConfiguration(transferManagerConfiguration);

        return transferManager;
    }

    public static void shutdownTransferManager(TransferManager transferManager) {
        // 指定参数为 true, 则同时会关闭 transferManager 内部的 COSClient 实例。
        // 指定参数为 false, 则不会关闭 transferManager 内部的 COSClient 实例。
        transferManager.shutdownNow(true);
    }


    public static List<DeleteObjectsRequest.KeyVersion> getKeyList(String bucketName, String key) {
        List<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<>();
        COSClient cosClient = createCOSClient();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucketName);
        listObjectsRequest.setPrefix(key);
        // 设置最大列出多少个对象, 一次 listobject 最大支持1000
        listObjectsRequest.setMaxKeys(2);
        // 保存列出的结果
        ObjectListing objectListing = null;
        try {
            objectListing = cosClient.listObjects(listObjectsRequest);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
// object summary 表示此次列出的对象列表
        List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
        for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
            // 对象的 key
            String key1 = cosObjectSummary.getKey();
            // 对象的etag
            String etag = cosObjectSummary.getETag();
            // 对象的长度
            long fileSize = cosObjectSummary.getSize();
            // 对象的存储类型
            String storageClasses = cosObjectSummary.getStorageClass();
            keyList.add(new DeleteObjectsRequest.KeyVersion(key1));
        }
// 确认本进程不再使用 cosClient 实例之后，关闭之
        cosClient.shutdown();
        return keyList;
    }

    public static DeleteObjectsResult batchDeleteKeys(String bucketname, List<DeleteObjectsRequest.KeyVersion> keys){
        COSClient cosClient = createCOSClient();
        String bucketName = bucketname;
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
        deleteObjectsRequest.setKeys(keys);
        DeleteObjectsResult deleteObjectsResult = null;
        try {
            deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
        } catch (MultiObjectDeleteException mde) {
            // 如果部分删除成功部分失败, 返回 MultiObjectDeleteException
            List<DeleteObjectsResult.DeletedObject> deleteObjects = mde.getDeletedObjects();
            List<MultiObjectDeleteException.DeleteError> deleteErrors = mde.getErrors();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }

// 确认本进程不再使用 cosClient 实例之后，关闭之
        cosClient.shutdown();
        return deleteObjectsResult;
    }

}
