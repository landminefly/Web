<script>
import darkModeBtn from './dark-mode-btn.vue';
export default {
    components: {
        darkModeBtn
    }, data() {
        return {
            isTitleShown: false,
        }
    },
    mounted(){
        this.$data.isTitleShown = true;
    }
}
</script>

<template>
    <div id="login-back-wrapper">
        <video id="login-back" muted autoplay loop>
            <source src="../assets/back-video.webm">
        </video>
    </div>
    <div id="login-wrapper">
        <Transition name="title">
            <div v-if="isTitleShown" id="title">
                <p>Welcome</p>
            </div>
        </Transition>
    </div>
</template>

<style>
#login-back-wrapper {
    position: fixed;
    top: 0;
    left: 0;
    z-index: -2;
    width: 100%;
    height: 100%;
    overflow: hidden;
}

#login-back {
    min-width: 100%;
    min-height: 100%;
}

body::after {
    content: "";
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
    background-color: rgba(0, 0, 0, 0.8);
}

#title {
    position: relative;
    top:100px;
    font-size: 100px;
    color: #f0f6fc;
}

#title p {
    text-align: center;
}

.title-enter-active {
    animation: scale-in-center 0.6s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
}

.title-leave-active {
    animation: scale-out-center 0.6s cubic-bezier(0.550, 0.085, 0.680, 0.530) both;
}

@keyframes scale-in-center {
    0% {
        transform: scale(0);
        opacity: 1;
    }

    100% {
        transform: scale(1);
        opacity: 1;
    }
}

@keyframes scale-out-center {
    0% {
        transform: scale(1);
        opacity: 1;
    }

    100% {
        transform: scale(0);
        opacity: 1;
    }
}
</style>