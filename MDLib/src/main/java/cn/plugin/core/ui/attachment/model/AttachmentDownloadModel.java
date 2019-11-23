package cn.plugin.core.ui.attachment.model;


import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import cn.plugin.core.api.BaseMainService;
import cn.plugin.core.api.ProgressCallback;
import cn.plugin.core.api.RetrofitResultListener;
import cn.plugin.core.utils.LogUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 附件下载
 */
public class AttachmentDownloadModel {
    private static final String TAG = AttachmentDownloadModel.class.getSimpleName();

    private Map<String, Call<ResponseBody>> downloadMap = new HashMap<>();

    public void download(final String downloadUrl, final String savePath, HashMap<String, String> params, final RetrofitResultListener<File> result) {
        ProgressCallback<ResponseBody> callback = new ProgressCallback<ResponseBody>() {
            private int lastProgress = 0;

            @Override
            public void onSuccess(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    LogUtil.d(TAG, "server contacted and has file");

                    new AsyncTask<Void, Long, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            boolean writtenToDisk = writeResponseBodyToDisk(response.body(), savePath);
                            LogUtil.d(TAG, "file download was a success? " + writtenToDisk);

                            if (writtenToDisk) {
                                result.onSuccess(new File(savePath));
                            } else {
                                result.onFailure("请稍后重试~");
                            }
                            result.onEnd();
                            downloadMap.remove(downloadUrl);
                            return null;
                        }
                    }.execute();
                } else {
                    LogUtil.d(TAG, "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                result.onFailure(t.getMessage());
                result.onEnd();
                downloadMap.remove(downloadUrl);
            }

            @Override
            public void onLoading(long total, long progress) {
                LogUtil.d(TAG, "total:" + total + ",progress:" + progress);
                //更新下载进度
//                long block = total / 100;// 百分之一是多少byte
//                result.onProgress((int) progress, (int) total, (int) (progress / block));
                notifyProgressUpdated(result, (int) progress, (int) total);
            }

            private void notifyProgressUpdated(RetrofitResultListener<File> listener, int wrote, int amount) {
                int progress = (int) ((((float) wrote) / amount) * 100);
                if (progress == 100 || (progress - lastProgress > 2 + 10 * Math.random())) {
                    lastProgress = progress;
                    int block = amount / 100;// 百分之一是多少byte
                    if (block == 0) {
                        block = 100;
                    }
                    listener.onProgress(wrote, amount, wrote / block);
                }
            }
        };

        Call<ResponseBody> call = BaseMainService.getAttachmentDownloadService("", callback).download(downloadUrl, params);
        call.enqueue(callback);
        downloadMap.put(downloadUrl, call);
    }

    /**
     * 取消下载
     *
     * @param downloadUrl
     */
    public void cancelDownload(String downloadUrl) {
        if (downloadMap.containsKey(downloadUrl)) {
            downloadMap.get(downloadUrl).cancel();
            downloadMap.remove(downloadUrl);
        }
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String fileSavePath) {
        try {
            String patientFilePath = fileSavePath.substring(0, fileSavePath.lastIndexOf("/"));
            // todo change the file location/name according to your needs
            File futureStudioIconFilepatient = new File(patientFilePath);
            if (!futureStudioIconFilepatient.exists()) {
                futureStudioIconFilepatient.mkdirs();
            }
            File futureStudioIconFile = new File(fileSavePath);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}