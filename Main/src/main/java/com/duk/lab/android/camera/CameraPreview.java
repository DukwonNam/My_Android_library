package com.duk.lab.android.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;

/**
 * Created by Duk on 2016-12-15.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CameraPreview";
    private Camera mCamera;

    public CameraPreview(Context context) {
        super(context);
        initViews();
    }

    private void initViews() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("test_duk", "surfaceCreated holder=" + holder);
        mCamera = CameraUtil.getCameraInstance();
        if (mCamera == null) {
            return;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("test_duk", "surfaceChanged width=" + width + ", height=" + height);
        Log.i("test_duk", "getWidth()=" + getWidth() + ", getHeight()=" + getHeight());
        if (holder.getSurface() == null || mCamera == null) {
            // preview surface does not exist
            return;
        }

        // Stop preview
        mCamera.stopPreview();

        setPreviewSize(width, height);
        setDisplayOrientation();

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    private void setPreviewSize(int width, int height) {
        final Camera.Parameters cameraParams = mCamera.getParameters();
        final Camera.Size cameraSize = getBestMatchedSize(cameraParams.getSupportedPreviewSizes(), width, height);
        cameraParams.setPreviewSize(cameraSize.width, cameraSize.height);
        mCamera.setParameters(cameraParams);
    }

    private Camera.Size getBestMatchedSize(List<Camera.Size> supportedSizeList, int width, int height) {
        Log.i("test_duk", "getBestMatchedSize width=" + width + ", height" + height);
        for (Camera.Size item :supportedSizeList) {
            Log.i("test_duk", "item.width=" + item.width + ", item.height" + item.height);
        }
        for (Camera.Size item :supportedSizeList) {
            if (item.width <= width && item.height <= height) {
                Log.i("test_duk", "bestMatched!! item.width=" + item.width + ", item.height" + item.height);
                return item;
            }
        }
        return null;
    }

    private void setDisplayOrientation() {
        final WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        int orientation = 0;
        final int windowRotation = wm.getDefaultDisplay().getRotation();
        Log.i("test_duk", "windowRotation=" + windowRotation);
        switch (windowRotation) {
            // Landscape on left side bottom
            case Surface.ROTATION_90:
                orientation = 0;
                break;
            // Landscape on right side bottom
            case Surface.ROTATION_270:
                orientation = 180;
                break;
            // Portrait
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
            default:
                orientation = 90;
                break;
        }
        mCamera.setDisplayOrientation(orientation);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("test_duk", "surfaceDestroyed holder=" + holder);
        if (mCamera == null) {
            return;
        }

        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }
}
