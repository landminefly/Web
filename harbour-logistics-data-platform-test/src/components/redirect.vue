<script>
export default {
    mounted() {
        if (this.$store.state.isLogged === 0) {
            this.$router.push('/login');
        } else if (this.$store.state.isLogged === 1) {
            this.$router.push('/cust');
        }else if (this.$store.state.isLogged === 2){
            this.$router.push('/admin');
        }
    }
}
</script>

<template>
    重定向界面
</template>