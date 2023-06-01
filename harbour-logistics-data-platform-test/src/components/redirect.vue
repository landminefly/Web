<script>
import axios from 'axios';
export default {
    mounted() {
        axios({
            method: "POST",
            url: "http://localhost:5173/hldp/",
            data: "",
        }).then(function (response) {
            if (response.data === "u") {
                return { name: "login" }
            }
        })
    }
}
</script>

<template>
    重定向界面
</template>