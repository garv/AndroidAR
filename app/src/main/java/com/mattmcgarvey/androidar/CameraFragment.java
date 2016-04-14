package com.mattmcgarvey.androidar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;

/**
 * Created by garv on 4/13/16.
 */
public class CameraFragment extends Fragment {

    private CameraDevice mCameraDevice;
    private CameraCaptureSession mCameraCaptureSession;

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {

            mCameraDevice = camera;
        }

        @Override
        public void onDisconnected(CameraDevice camera) {

            mCameraDevice.close();
        }

        @Override
        public void onError(CameraDevice camera, int error) {

            mCameraDevice.close();
        }
    };

    private void startCamera() {

        final Activity activity = getActivity();

        final CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);

        try {
            for (final String cameraId : manager.getCameraIdList()) {

                final CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);

                final Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);

                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }
}
