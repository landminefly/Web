<template>
  访客界面
  <button @click="custLogout">登出</button>
</template>

<script>
export default {
  methods:{
    custLogout(){
      this.$store.commit('logout');
      this.$router.push('/login');
    }
  }
}
</script>