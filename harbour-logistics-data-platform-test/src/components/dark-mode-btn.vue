<script>
import '../assets/iconfont.css';
</script>

<template>
    <div id="dark-mode-btn" :class="{active:this.$store.state.isDarkMode}" @click="this.$store.commit('toggleDarkMode')">
        <span class="iconfont icon-taiyang"></span>
        <span class="iconfont icon-yueliang"></span>
        <div id="btn-inner"></div>
    </div>
</template>

<style>
#dark-mode-btn {
    position: relative;
    left: 40px;
    top: 2px;
    height: 30px;
    width: 60px;
    border: 3px #010409 solid;
    color: #010409;
    border-radius: 25px;
    transition: all 0.3s ease;
}

#btn-inner {
    position: relative;
    height: 30px;
    width: 30px;
    top: -1px;
    left: -1px;
    background-color: #010409;
    border: 1px #010409 solid;
    border-radius: 50%;
    transition: all 0.3s ease;
}

#dark-mode-btn.active {
    color: #f0f6fc;
    border: 3px #f0f6fc solid;
}

#dark-mode-btn.active #btn-inner {
    background-color: #f0f6fc;
    border: 1px #f0f6fc solid;
    left: 30px;
}

.icon-taiyang {
    position: absolute;
    font-size: 45px;
    left: -45px;
    top: -7px;
}

.icon-yueliang {
    position: absolute;
    font-size: 32px;
    left: 65px;
    top: 0px;
}
</style>