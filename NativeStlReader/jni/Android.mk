LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := NativeStlReader
LOCAL_SRC_FILES := StlReader.cpp ConstomGLU.cpp NativeStlReader.cpp
LOCAL_LDLIBS    := -llog -landroid -lEGL -lGLESv1_CM
include $(BUILD_SHARED_LIBRARY)
