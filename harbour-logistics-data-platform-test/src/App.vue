<script>
export default {
  mounted() {
    document.body.style.backgroundColor = this.$store.state.isDarkMode ? '#010409' : '#f0f6fc';
    document.body.style.color = this.$store.state.isDarkMode ? '#f0f6fc' : '#010409';
  },
  watch: {
    '$store.state.isDarkMode'(newValue) {
      document.body.style.backgroundColor = newValue ? '#010409' : '#f0f6fc';
      document.body.style.color = newValue ? '#f0f6fc' : '#010409';
    }
  }
}
</script>

<template>
  <router-view></router-view>
</template>