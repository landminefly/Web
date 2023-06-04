<script>
import module1 from './module1.vue';
import module2 from './module2.vue';
import darkModeBtn from './dark-mode-btn.vue';
export default {
  components: {
    module1,
    module2,
    darkModeBtn,
  },
  data() {
    return {
      //侧边栏、模块wrapper的高
      adminModuleSideHeight: '740px',
      //分别是模块wrapper的宽、动画
      //在收起、展开侧边栏时需要使用
      adminModuleWrapperWidth: '85%',
      adminModuleWrapperTransition: 'all 0.3s',
      //选择了哪个模块，从1开始
      selectWhich: 1,
      //是否展开侧边栏
      isSidebarShown: true,
      darkModeColor: {
        header: null,
        sideBar: null,
        module: null,
        border: null,
      },
    }
  },
  methods: {
    //登出
    adminLogout() {
      this.$store.commit('logout');
      this.$router.push('/login');
    },
    //当视口变化时，需要判断并更改侧边栏、模块wrapper的高
    updateAdminModuleSideHeight() {
      const windowInnerHeight = window.innerHeight;
      if (windowInnerHeight < 800) {
        this.adminModuleSideHeight = `740px`;
      } else {
        this.adminModuleSideHeight = `${windowInnerHeight - 60}px`;
      }
    },
  },
  watch: {
    //展开、收起侧边栏的模块wrapper的样式和动画设置
    isSidebarShown(newValue) {
      if (newValue === false) {
        this.adminModuleWrapperWidth = '100%';
        this.adminModuleWrapperTransition = 'all 0.3s';
      } else if (newValue === true) {
        this.adminModuleWrapperWidth = '85%';
        this.adminModuleWrapperTransition = 'all 0.3s 0.3s';
      }
    },
    //更改黑夜模式#010409' : '#f0f6fc'
    '$store.state.isDarkMode': {
      handler(newValue) {
        this.darkModeColor.header = newValue ? '#0d1117' : '#f6f8fa';
        this.darkModeColor.sideBar = newValue ? '#0d1117' : '#f6f8fa';
        this.darkModeColor.module = newValue ? '#010409' : '#ffffff';
        this.darkModeColor.border = newValue ? '#21262d' : '#d0d7de';
      },
      //页面首次加载时初始化
      immediate: true
    }
  },
  mounted() {
    // 监听视口改变事件
    window.addEventListener('resize', this.updateAdminModuleSideHeight);
    this.updateAdminModuleSideHeight()
  },
  beforeDestroy() {
    // 移除视口改变事件监听器
    window.removeEventListener('resize', this.updateAdminModuleSideHeight);
  },
}

</script>

<template>

  <div id="admin-wrapper">

    <!-- 头部 -->
    <div id="admin-header" :style="{ backgroundColor: darkModeColor.header, borderColor: darkModeColor.border }">
      
      <div id="admin-title-wrapper">
        <div id="admin-title">永雏塔菲😘😘😘</div>
      </div>
      
      <div id="admin-dark-mode-btn-wrapper">
        <div>
          <darkModeBtn></darkModeBtn>
        </div>
      </div>

      <button @click="adminLogout" id="admin-logout"></button>
      <label for="admin-logout" id="admin-logout-label">登出</label>
    
    </div>

    <!-- 侧边栏 -->
    <Transition name="admin-sidebar">
      <div id="admin-sidebar" v-if="isSidebarShown"
        :style="{ height: adminModuleSideHeight, backgroundColor: darkModeColor.sideBar, borderColor: darkModeColor.border }">
        <div id="collapse-sidebar-btn" @click="isSidebarShown = false">
          <span>收起侧边栏</span>&nbsp&nbsp&nbsp&nbsp&nbsp<span class="iconfont icon-zuojiantou"></span>
        </div>
        <div id="module1-nav" @click="selectWhich = 1" :class="{ active: selectWhich === 1 }">
          模块1
        </div>
        <div id="module2-nav" @click="selectWhich = 2" :class="{ active: selectWhich === 2 }">
          模块2
        </div>
      </div>
    </Transition>

    <!-- 展开侧边栏按钮 -->
    <Transition name="expand-sidebar-btn">
      <div id="expand-sidebar-btn" v-if="isSidebarShown === false" @click="isSidebarShown = true">
        <span>展开侧边栏</span>&nbsp&nbsp&nbsp&nbsp&nbsp<span class="iconfont icon-youjiantou"></span>
      </div>
    </Transition>

    <!-- 模块wrapper -->
    <div id="admin-module-wrapper"
      :style="{ height: adminModuleSideHeight, width: adminModuleWrapperWidth, transition: adminModuleWrapperTransition, backgroundColor: darkModeColor.module }">
      <Transition name="module" mode="out-in">
        <module1 v-if="selectWhich === 1"></module1>
        <module2 v-else-if="selectWhich === 2"></module2>
      </Transition>
    </div>

  </div>
</template>

<style>

/* 页面主样式 */
#admin-wrapper {
  width: 100%;
  min-height: 800px;
  height: 100vh;
  position: relative;
}

/* 以下为顶部样式 */
#admin-header {
  height: 60px;
  width: 100%;
  border-bottom: 1px solid;
  box-sizing: border-box;
  position: absolute;
  transition: all 0.3s;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  /* 第一 */
  z-index: 999;
}

/* 标题样式 */
#admin-title-wrapper {
  position: absolute;
  left: 30px;
  height: 60px;
  line-height: 60px;
  width: 300px;
  font-size: 30px;
  font-weight: 900;
  text-align: start;
}

/* 登出按钮样式 */
#admin-logout {
  display: none;
}

#admin-logout-label {
  cursor: pointer;
  display: block;
  height: 35px;
  width: 60px;
  line-height: 35px;
  text-align: center;
  font-weight: 900;
  border-radius: 10px;
  background-color: rgba(255, 0, 0, 1);
  position: relative;
  top: 50%;
  left: -10px;
  transform: translateY(-50%);
  transition: all 0.2s;
}

#admin-logout-label:hover {
  transform: translateY(-50%) scale(1.1);
}

#admin-logout-label:active {
  transform: translateY(-50%) scale(0.9);
}

/* 黑夜模式按钮样式 */
#admin-dark-mode-btn-wrapper {
  height: 100%;
  width: 130px;
}

#admin-dark-mode-btn-wrapper>div {
  position: absolute;
  top: 11px;
  transform: scale(0.6);
}

/* 以下为左侧导航栏样式 */
#admin-sidebar {
  width: 15%;
  padding-top: 100px;
  border-right: 1px solid;
  box-sizing: border-box;
  position: absolute;
  top: 60px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  /* 第二 */
  z-index: 99;
}

/* 模块名样式 */
#admin-sidebar>:not(:first-child) {
  cursor: pointer;
  text-align: center;
  font-size: 20px;
  line-height: 90px;
  height: 90px;
  width: 100%;
  position: relative;
  transition: all 0.2s;
}

#admin-sidebar>:not(:first-child)::after {
  content: '';
  position: absolute;
  display: block;
  border-bottom: 2px rgba(100, 100, 100, 0.8) solid;
  width: 80%;
  left: 10%;
  top: 70px;
  transition: all 0.2s;
}

#admin-sidebar>:not(:first-child).active {
  font-weight: 900;
  color: rgba(255, 0, 0, 1);
}

#admin-sidebar>:not(:first-child).active::after {
  border-width: 4px;
  border-color: rgba(255, 0, 0, 1);
}

#admin-sidebar>:not(:first-child):not(.active):hover {
  font-weight: 900;
  color: rgba(128, 0, 128, 0.8);
}

#admin-sidebar>:not(:first-child):not(.active):hover::after {
  border-width: 4px;
  border-color: rgba(128, 0, 128, 0.8);
}

/* 收起侧边栏按钮样式 */
#collapse-sidebar-btn {
  cursor: pointer;
  height: 50px;
  width: 170px;
  border-radius: 0 10px 10px 0;
  text-align: center;
  font-size: 18px;
  line-height: 50px;
  background-color: #d2691e;
  position: absolute;
  top: 5px;
  left: -120px;
  transition: all 0.3s;
}

/* 左侧导航栏收起动画 */
.admin-sidebar-leave-active {
  transition: all 0.3s;
}

.admin-sidebar-enter-active {
  transition: all 0.3s 0.29s;
}

.admin-sidebar-leave-to,
.admin-sidebar-enter-from {
  transform: translateX(-100%);
}

#collapse-sidebar-btn>span:last-child {
  font-weight: 900;
}

#collapse-sidebar-btn:hover {
  left: 0;
}

/* 展开侧边栏按钮样式 */
#expand-sidebar-btn {
  cursor: pointer;
  height: 50px;
  width: 180px;
  border-radius: 0 10px 10px 0;
  text-align: center;
  font-size: 20px;
  line-height: 50px;
  background-color: chocolate;
  position: absolute;
  top: 65px;
  left: -130px;
  /* 第三 */
  z-index: 99;
  transition: all 0.3s;
}

#expand-sidebar-btn>span:last-child {
  font-weight: 900;
}

#expand-sidebar-btn:hover {
  left: 0;
}

.expand-sidebar-btn-enter-active {
  transition: all 0.3s 0.3s;
}

.expand-sidebar-btn-leave-active {
  transition: all 0.3s;
}

.expand-sidebar-btn-enter-from,
.expand-sidebar-btn-leave-to {
  transform: translateX(-100%);
}

/* 模块wrapper样式 */
#admin-module-wrapper {
  position: absolute;
  right: 0;
  top: 60px;
  /* 第三 */
  z-index: 9;
}

.module-enter-active,
.module-leave-active {
  transition: opacity 0.3s;
}

.module-enter-from,
.module-leave-to {
  opacity: 0;
}
</style>