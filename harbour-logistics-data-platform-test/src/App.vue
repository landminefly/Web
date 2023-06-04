<script>
export default {
  watch: {
    //初始化/更改全局黑夜模式
    '$store.state.isDarkMode': {
      handler(newValue) {
        document.body.style.backgroundColor = newValue ? '#010409' : '#ffffff';
        document.body.style.color = newValue ? '#e1e1e1' : '#213547';
      },
      immediate: true
    },
    '$store.state.isLogged': {
      handler(newValue) {
        switch (newValue) {
          case 0:
            document.body.className = 'login';
            return;
          case 1:
            document.body.className = 'cust';
            return;
          case 2:
            document.body.className = 'admin';
            return;
        }
      },
      immediate: true
    }
  }
}
</script>

<template>
  <router-view></router-view>
</template>

<style>

</style>