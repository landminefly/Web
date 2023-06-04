<script>
import axios from 'axios'
import darkModeBtn from './dark-mode-btn.vue';
export default {
    components: {
        darkModeBtn
    },
    data() {
        return {
            //0表示尚未选择，1表示选择了客户登录，2表示选择了管理员登录
            selectWhich: 0,
            //黑夜模式样式
            isDarkMode: {
                backgroundColor: null,
                toggleBackgroundColor: null,
            },
            //焦点是否在用户名输入框
            isUserFormFocus: false,
            //焦点是否在密码输入框
            isPwdFormFocus: false,
            //用户名输入
            userInput: '',
            //密码输入
            pwdInput: '',
            //错误框信息
            isErrorOccur: {
                errorCode: 0,
                errorMessage: ['', '请输入用户名！', '请输入密码！', '用户名或密码不正确！'],
                //shake动画
                shake: false
            }
        }
    },
    methods: {
        //管理员登录验证
        adminLogin() {
            if (this.userInput === '') {
                this.isErrorOccur.errorCode = 1;
                this.setShake();
                return;
            } else if (this.pwdInput === '') {
                this.isErrorOccur.errorCode = 2;
                this.setShake();
                return;
            }
            var that = this;
            axios({
                method:"POST",
                url:"/api/HLDP/com/admin/login",
                data:{
                    //明文传输，不管了XD
                    username: this.userInput,
                    pwd: this.pwdInput,
                }
            }).then(value =>{
                if(value.data === 'success'){
                    this.$store.commit('loginAsAdmin');
                    this.$router.push('/admin');
                }else if(value.data === 'fail'){
                    this.isErrorOccur.errorCode = 3;
                    this.setShake();
                }else{
                    console.log(value);
                }
            }).catch(reason =>{
                console.log(reason);
            })
        },
        setShake() {
            this.isErrorOccur.shake = false;
            setInterval(() => {
                this.isErrorOccur.shake = true;
            }, 1);
        },
        //返回选择角色界面
        backToSelect(){
            this.selectWhich = 0;
            this.isErrorOccur.errorCode = 0;
        },
        //顾客直接登录
        cusLogin() {
            this.$store.commit('loginAsCust');
            this.$router.push('/cust');
        }
    },
    watch: {
        //更改黑夜模式
        '$store.state.isDarkMode': {
            handler(newValue) {
                this.isDarkMode.backgroundColor = newValue ? 'rgba(0, 0, 0, 0.7)' : 'rgba(255, 255, 255, 0.6)';
                this.isDarkMode.toggleBackgroundColor = newValue ? 'rgba(255, 255, 255, 0.6)' : 'rgba(0, 0, 0, 0.7)';
            },
            //页面首次加载时初始化
            immediate: true
        }
    }
}


</script>

<template>
    <!-- 视频背景 -->
    <div id="login-back-wrapper">
        <video id="login-back" muted autoplay loop>
            <source src="../assets/back-video.webm">
        </video>
    </div>
    <div id="login-wrapper">
        <Transition name="login-wrapper" mode="out-in" appear>
            <!-- 登录选择界面 -->
            <div v-if="selectWhich === 0" id="login-select-wrapper">
                <div id="title">
                    <p>永雏塔菲😘😘😘</p>
                </div>
                <div id="cus-login-wrapper" @click="selectWhich = 1" @click.prevent="cusLogin"
                    :style="{ backgroundColor: isDarkMode.backgroundColor }">
                    <p>访客登录</p>
                    <span class="iconfont icon-kehu"></span>
                </div>
                <div id="admin-login-wrapper" @click="selectWhich = 2"
                    :style="{ backgroundColor: isDarkMode.backgroundColor }">
                    <p>管理员登录</p>
                    <span class="iconfont icon-guanliyuan"></span>
                </div>
            </div>
            <!-- 管理员登录界面 -->
            <div v-else-if="selectWhich === 2" id="login-admin-wrapper"
                :style="{ backgroundColor: isDarkMode.backgroundColor }">
                <label for="admin-back" id="admin-back-label">
                    <span class="iconfont icon-fanhui"></span><span>返回</span>
                </label>
                <button id="admin-back" @click="backToSelect"></button>
                <form>
                    <div>
                        <span>用户名：</span>
                        <input type="text" id="username" :class="{ focus: isUserFormFocus }" v-model="userInput"
                            @focus="isUserFormFocus = true" @blur="isUserFormFocus = false">
                    </div>
                    <div>
                        <span>密码：</span>
                        <input type="password" id="pwd" :class="{ focus: isPwdFormFocus }" v-model="pwdInput"
                            @focus="isPwdFormFocus = true" @blur="isPwdFormFocus = false">
                    </div>
                    <div>
                        <button id="submit" @click.prevent="adminLogin"></button>
                        <label for="submit" id="submit-label">
                            <span>提交</span>
                        </label>
                    </div>
                </form>
                <Transition name="error">
                    <div id="errorMessage" v-if="isErrorOccur.errorCode != 0" :class="{ loginShake: isErrorOccur.shake }">
                        <span class="iconfont icon-tanhao"></span>
                        {{ this.isErrorOccur.errorMessage[this.isErrorOccur.errorCode] }}
                    </div>
                </Transition>
            </div>
        </Transition>

        <!-- 黑夜模式按钮 -->
        <Transition name="login-dark-mode-btn" appear>
            <div id="login-dark-mode-btn-wrapper" ref="darkModeBtn"
                :style="{ backgroundColor: isDarkMode.backgroundColor }">
                <div>
                    <darkModeBtn></darkModeBtn>
                </div>
            </div>
        </Transition>
    </div>
</template>

<style>
/* 背景样式设置 */
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
    position: absolute;
}

body.login::after {
    content: "";
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
    background-color: rgba(0, 0, 0, 0.8);
}

/* 登录界面主样式 */
#login-wrapper {
    position: relative;
    min-height: 800px;
    height: 100vh;
}

.login-wrapper-enter-active {
    animation: fade-in-fwd 0.3s cubic-bezier(0.390, 0.575, 0.565, 1.000) both;
}

.login-wrapper-leave-active {
    animation: fade-out-bck 0.3s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
}

/* 以下为登录选择页面样式 */

/* 标题样式 */
#title {
    color: #f0f6fc;
    position: absolute;
    top: 100px;
    font-size: 100px;
    left: 0;
    right: 0;
    margin: auto;
}

#title p {
    text-align: center;
}

/* 客户登录框样式 */
#cus-login-wrapper {
    position: absolute;
    height: 300px;
    width: 200px;
    border-radius: 50px;
    top: 300px;
    left: 30%;
    cursor: pointer;
    transition: all 0.2s;
}

#cus-login-wrapper:hover {
    transform: scale(1.1);
}

#cus-login-wrapper:active {
    transform: scale(1);
}

#cus-login-wrapper p {
    position: relative;
    text-align: center;
    top: 20%;
    font-size: 30px;
}

#cus-login-wrapper span {
    position: absolute;
    top: 45%;
    left: 50%;
    transform: translateX(-50%);
    font-size: 100px;
    color: #95d2f2;
}

/* 管理员登录框样式 */
#admin-login-wrapper {
    position: absolute;
    height: 300px;
    width: 200px;
    border-radius: 50px;
    top: 300px;
    left: 60%;
    cursor: pointer;
    transition: all 0.2s;
}

#admin-login-wrapper:hover {
    transform: scale(1.1);
}

#admin-login-wrapper:active {
    transform: scale(1);
}

#admin-login-wrapper p {
    position: relative;
    text-align: center;
    top: 20%;
    font-size: 30px;
}

#admin-login-wrapper span {
    position: absolute;
    top: 45%;
    left: 50%;
    transform: translateX(-50%);
    font-size: 100px;
    color: #fa6663;
}

/* 以下为登录管理员页面样式 */

/* 设置wrapper样式 */
#login-admin-wrapper {
    height: 500px;
    width: 800px;
    position: absolute;
    left: 0;
    right: 0;
    top: 150px;
    margin: auto;
    border-radius: 10px;
    transition: all 0.2s;
}

/* 设置返回键样式 */
#admin-back {
    display: none;
}

#admin-back-label {
    display: block;
    height: 30px;
    width: 80px;
    position: relative;
    top: 20px;
    left: 20px;
    text-align: center;
    line-height: 30px;
    cursor: pointer;
    transition: all 0.2s;
}

#admin-back-label>span {
    font-size: 20px;
}

#admin-back-label:hover {
    transform: scale(1.1);
}

#admin-back-label:active {
    transform: scale(1);
}

/* 设置表单样式 */

#login-admin-wrapper form {
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: flex-end;
    height: 400px;
    width: 410px;
    font-size: 20px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translateX(-52%) translateY(-50%);
}

#login-admin-wrapper form span {
    display: inline-block;
    width: 80px;
}

#login-admin-wrapper form input {
    color: #000;
    background-color: #b0b0b0;
    border: 1px rgb(100, 100, 100) solid;
    height: 30px;
    width: 300px;
    padding: 5px 10px;
    font-size: 20px;
    border-radius: 10px;
    transition: all 0.2s;
}

#login-admin-wrapper form input.focus {
    outline: 3px solid rgb(150, 0, 150);
    outline-offset: -3px;
}

/* 设置提交按钮样式 */
#submit {
    display: none;
}

#submit-label {
    display: block;
    height: 50px;
    width: 100px;
    line-height: 50px;
    text-align: center;
    border-radius: 20px;
    background-color: rgba(255, 255, 255, 0.6);
    color: #010409;
    position: absolute;
    bottom: 30px;
    right: 120px;
    cursor: pointer;
    transition: all 0.2s;
}

#submit-label:hover {
    transform: scale(1.1);
}

#submit-label:active {
    transform: scale(1);
}

/* 设置错误提示框样式 */
#errorMessage {
    height: 50px;
    width: 250px;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    font-weight: 700;
    background-color: rgba(255, 0, 0, 0.3);
    border-radius: 20px;
    position: absolute;
    bottom: 145px;
    left: 305px;
}

#errorMessage span {
    font-weight: 100;
    font-size: 20px;
    color: red;
}

.error-enter-active {
    transition: all 0.3s;
}

.error-enter-from {
    opacity: 0;
}

#errorMessage.loginShake {
    animation: shake-horizontal 0.8s cubic-bezier(0.455, 0.030, 0.515, 0.955) both;
}

/* 设置黑夜模式转换按钮样式 */
#login-dark-mode-btn-wrapper {
    height: 80px;
    width: 180px;
    position: absolute;
    right: 5%;
    bottom: 8%;
    border-radius: 20px;
}

#login-dark-mode-btn-wrapper>div {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translateX(-110%) translateY(-52%);
}

.login-dark-mode-btn-enter-active {
    animation: fade-in-fwd 0.3s cubic-bezier(0.390, 0.575, 0.565, 1.000) both;
}

.login-dark-mode-btn-leave-active {
    animation: fade-out-bck 0.3s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
}

/* 动画样式 */
@keyframes fade-in-fwd {
    0% {
        transform: translateZ(-80px);
        opacity: 0;
    }

    100% {
        transform: translateZ(0);
        opacity: 1;
    }
}

@keyframes fade-out-bck {
    0% {
        transform: translateZ(0);
        opacity: 1;
    }

    100% {
        transform: translateZ(-80px);
        opacity: 0;
    }
}

@keyframes shake-horizontal {

    0%,
    100% {
        transform: translateX(0);
    }

    10%,
    30%,
    50%,
    70% {
        transform: translateX(-10px);
    }

    20%,
    40%,
    60% {
        transform: translateX(10px);
    }

    80% {
        transform: translateX(8px);
    }

    90% {
        transform: translateX(-8px);
    }
}
</style>