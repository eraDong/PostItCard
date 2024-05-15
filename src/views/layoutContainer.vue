<script setup>
import { ref } from 'vue'
import { addCard } from '@/api/card'
import { useRouter } from "vue-router";
import router from '@/router';


const Router = useRouter()
let dialogVisible = ref(false)
let cardTitle = ref('')
let cardBody = ref('')
let expirationDate = ref('')

const addButton = () => {
    dialogVisible.value = true
}

const resetContent = () => {
    cardTitle.value = ''
    cardBody.value = ''
    expirationDate.value = ''
}

const addContent = async () => {
    const cardData = new FormData()
    const currentDate = new Date();
    const formattedCurrentDate = `${currentDate.getFullYear()}-${('0' + (currentDate.getMonth() + 1)).slice(-2)}-${('0' + currentDate.getDate()).slice(-2)}`;
    const formattedExpirationDate = `${expirationDate.value.getFullYear()}-${('0' + (expirationDate.value.getMonth() + 1)).slice(-2)}-${('0' + expirationDate.value.getDate()).slice(-2)}`
    cardData.append('cardTitle', cardTitle.value)
    cardData.append('cardBody', cardBody.value)
    cardData.append('publicationDate', formattedCurrentDate)
    cardData.append('expirationDate', formattedExpirationDate)
    cardData.append('visible', 1)
    await addCard(cardData)
    dialogVisible.value = false
    
    Router.go(0)
}

const handleClosed = () => {
    resetContent()
}




</script>
<template>
    <div>
        <router-link to="/home">HOME</router-link>
        <router-link to="/bin">BIN</router-link>
        <a @click="addButton()" href="#">ADD</a>
        <router-link to="/about">ABOUT</router-link>
    </div>
    <div>
        <router-view></router-view>
    </div>
    <div>
        <!-- add浮窗 -->
        <el-dialog
        v-model="dialogVisible"
        width="500"
        @closed="handleClosed"
        >
            Title:<el-input v-model="cardTitle"></el-input>
            Body:<el-input v-model="cardBody"></el-input>
            ExpirationDate:
            <br>
            <el-date-picker
            v-model="expirationDate"
            type="date">
            </el-date-picker>
            <br>
            <br>
            <el-button @click="addContent()">ADD</el-button>
            <el-button @click="resetContent()">RESET</el-button>
        </el-dialog>
    </div>
</template>
<style lang="less" scoped>
    :deep(.el-dialog) {
    .el-dialog__title {
        font-weight: 700;   
        color: hsla(160, 100%, 37%, 1);
    }
    .el-dialog__body {
        color: hsla(160, 100%, 37%, 1);
    }
    background-color: var(--el-text-color-primary)
    }

    .el-input {
      color: hsla(160, 100%, 37%, 1);

    }
    :deep(.el-input__wrapper) {
        background-color: transparent !important;
    }
    :deep(.el-input__inner) {
    color: hsla(160, 100%, 37%, 1) !important;
    }
    .el-button {
        color: hsla(160, 100%, 37%, 1);
        background-color: transparent;
    }


</style>