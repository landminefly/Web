<script>
import fileUpload from './file-upload.vue';
export default {
    components:{
        fileUpload,
    }
}
</script>

<template>
    <div id="module1-wrapper">
        <div id="file-upload-wrapper">
            <fileUpload></fileUpload>
        </div>
    </div>
</template>

<style>
#module1-wrapper {
    height: 100%;
    width: 100%;
}
</style>