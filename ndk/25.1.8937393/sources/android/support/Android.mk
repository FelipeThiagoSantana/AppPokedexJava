LOCAL_PATH := $(call my-dir)

# libandroid_support is only needed on LP32.
ifeq ($(filter $(NDK_KNOWN_DEVICE_ABI64S),$(TARGET_ARCH_ABI)),)

ifneq ($(LIBCXX_FORCE_REBUILD),true) # Using prebuilt

LIBCXX_LIBS := $(SYSROOT_LIB_DIR)

include $(CLEAR_VARS)
LOCAL_MODULE := android_support
LOCAL_SRC_FILES := $(LIBCXX_LIBS)/lib$(LOCAL_MODULE)$(TARGET_LIB_EXTENSION)
include $(PREBUILT_STATIC_LIBRARY)

else # Building

android_support_cflags := \
    -D_GNU_SOURCE \
    -Drestrict=__restrict__ \
    -ffunction-sections \
    -fdata-sections \
    -fvisibility=hidden \

android_support_c_includes := \
    $(BIONIC_PATH)/libc \
    $(BIONIC_PATH)/libc/upstream-openbsd/android/include \
    $(BIONIC_PATH)/libm \
    $(BIONIC_PATH)/libm/upstream-freebsd/android/include \

android_support_cflags += \
    -include freebsd-compat.h \
    -include openbsd-compat.h \
    -D__BIONIC_BUILD_FOR_ANDROID_SUPPORT \
    -Werror \

android_support_sources := \
    $(BIONIC_PATH)/libc/bionic/c32rtomb.cpp \
    $(BIONIC_PATH)/libc/bionic/locale.cpp \
    $(BIONIC_PATH)/libc/bionic/mbrtoc32.cpp \
    $(BIONIC_PATH)/libc/bionic/wchar.cpp \
    $(BIONIC_PATH)/libc/upstream-openbsd/lib/libc/locale/mbtowc.c \
    $(BIONIC_PATH)/libc/upstream-openbsd/lib/libc/locale/wcstombs.c \
    $(BIONIC_PATH)/libc/upstream-openbsd/lib/libc/locale/mbstowcs.c \
    src/locale_support.cpp \
    src/swprintf.cpp \
    src/wcstox.cpp \

include $(CLEAR_VARS)
LOCAL_MODULE := android_support
LOCAL_SRC_FILES := $(android_support_sources)
LOCAL_C_INCLUDES := $(android_support_c_includes)
LOCAL_CFLAGS := $(android_support_cflags)

LOCAL_CPPFLAGS := \
    -fvisibility-inlines-hidden \
    -std=gnu++11 \

include $(BUILD_STATIC_LIBRARY)

endif # Prebuilt/building

endif # LP32
