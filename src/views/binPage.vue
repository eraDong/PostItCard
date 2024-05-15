<script setup>
// 导入
import {ref} from 'vue'
import {getAllCards,deleteCard,updateCard} from '@/api/card'


// 变量
let dialogVisible = ref(false)
let cardArr = ref([])
let cardId = ref('')
let cardTitle = ref('')
let cardBody = ref('')
let publicationDate = ref('')
let expirationDate = ref('')
let editMode = ref(false)
let checkedList = ref([])
let isChecked = ref(true)

const showCardBody = (index) => {
    dialogVisible.value = true
    cardTitle.value = cardArr.value.data[index].cardTitle
    cardBody.value = cardArr.value.data[index].cardBody
    publicationDate.value = cardArr.value.data[index].publicationDate
    expirationDate.value = cardArr.value.data[index].expirationDate
    cardId.value = cardArr.value.data[index].cardId
}

const getAllCardsService = async () => {
    cardArr.value = await getAllCards()
}
getAllCardsService()

const formatISODate = (isoString) => {
      const date = new Date(isoString)
      const year = date.getFullYear()
      const month = (date.getMonth() + 1).toString().padStart(2, '0')
      const day = date.getDate().toString().padStart(2, '0')
      return `${year}-${month}-${day}`
}

const deleteCardService = async (id) => {
    await deleteCard(id)
    getAllCardsService()
}

const handleClosed = async (cardId) => {
    const cardData = new FormData()
    const formattedExpirationDate = formatISODate(expirationDate.value)
    const formattedpublicationDate =  formatISODate(publicationDate.value)
    cardData.append('cardTitle', cardTitle.value)
    cardData.append('cardBody', cardBody.value)
    cardData.append('publicationDate', formattedpublicationDate )
    cardData.append('expirationDate', formattedExpirationDate )
    cardData.append('visible', 1)
    if(editMode.value===true) {
        await updateCard(cardId,cardData)
    }

    cardTitle.value = ''
    cardBody.value = ''
    publicationDate.value = ''
    expirationDate.value = ''
    editMode.value = false
    getAllCardsService()
}

const editCard = async (cardId) => {
    editMode.value = !editMode.value
    const cardData = new FormData()
    const formattedExpirationDate = formatISODate(expirationDate.value)
    const formattedpublicationDate =  formatISODate(publicationDate.value)
    cardData.append('cardTitle', cardTitle.value)
    cardData.append('cardBody', cardBody.value)
    cardData.append('publicationDate', formattedpublicationDate )
    cardData.append('expirationDate', formattedExpirationDate )
    cardData.append('visible', 1)
    if(editMode.value===false) {
        await updateCard(cardId,cardData)
    }
    getAllCardsService()
}

const handleCheckboxClick = async (index,cardId) => {
    // 设置一个延迟，以模拟慢速执行
    await new Promise(resolve => setTimeout(resolve, 250));

    cardTitle.value = cardArr.value.data[index].cardTitle
    cardBody.value = cardArr.value.data[index].cardBody
    publicationDate.value = cardArr.value.data[index].publicationDate
    expirationDate.value = cardArr.value.data[index].expirationDate

    const cardData = new FormData()
    const formattedExpirationDate = formatISODate(expirationDate.value)
    const formattedpublicationDate =  formatISODate(publicationDate.value)
    cardData.append('cardTitle', cardTitle.value)
    cardData.append('cardBody', cardBody.value)
    cardData.append('publicationDate', formattedpublicationDate )
    cardData.append('expirationDate', formattedExpirationDate )
    cardData.append('visible', 1)
    await updateCard(cardId,cardData)

    
    getAllCardsService()


    // 设置一个延迟，以模拟慢速执行
    await new Promise(resolve => setTimeout(resolve, 250));
}

</script>
<template>
    <div class="main">
        <h2>FINISHED ASSIGNMENTS</h2>
        <div class="content">
            <el-card  v-show="!item.visible" v-for="(item,index) in cardArr.data" :key="item" shadow="hover" @click="showCardBody(index)">
                <div class="cardContent">
                    <div>{{ item.cardTitle }}</div>
                    <el-checkbox v-model="isChecked" @click.prevent.stop="handleCheckboxClick(index,item.cardId)"></el-checkbox>
                </div>
            </el-card>
            
        </div>

        <!-- 卡片浮窗 -->
        <el-dialog
        v-model="dialogVisible"
        width="1000px"

        @closed="handleClosed(cardId)"
        >
        <!-- header -->
            <div class="header" v-if="editMode===false">{{cardTitle}}</div>
            <div class="header" v-if="editMode===true">
                <el-input v-model="cardTitle"></el-input>
            </div>

        <!-- body -->
            <div class="bodyContent" v-if="editMode===false">{{cardBody}}</div>
            <div class="bodyContent" v-if="editMode===true">
                <el-input v-model="cardBody" type="textarea" :rows="5"></el-input>
            </div>

        <!-- date -->
            <div class="date">
                
                <div class="startDate" v-if="editMode===false">
                    <div>Start: <div>{{formatISODate(publicationDate)}}</div></div>
                </div>
                <div class="startDate" v-if="editMode===true">
                    <div>Start: <el-date-picker v-model="publicationDate" type="date" style="width: 100%;"></el-date-picker></div>
                </div>

                <div class="endDate" v-if="editMode===false">
                    <div>End: <div style="color: red;">{{formatISODate(expirationDate)}}</div></div>
                </div>
                <div class="endDate" v-if="editMode===true">
                    <div>End: <el-date-picker v-model="expirationDate" type="date" style="width: 100%;"></el-date-picker></div>
                </div>

            </div>

        <!-- buttons -->
            <div class="bodyButtons">
                <el-button type="text" @click="editCard(cardId)">EDIT</el-button>
                <el-button type="text" @click="deleteCardService(cardId)">DELETE</el-button>
            </div>
        </el-dialog>
    </div>


</template>
<style lang="less" scoped>
    .main {
        margin-top: 120px;
        animation: fadeIn 0.25s ease-out;
        
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        .content {
            
            .cardContent {
                display: flex;
                justify-content: space-between;
            }

            .el-checkbox {
                zoom:200%;
                :deep(.el-checkbox__input.is-checked .el-checkbox__inner) {
                    background-color:hsla(160, 100%, 37%, 1) !important;
                    border-color: hsla(160, 100%, 37%, 1) !important;
                }
            }
            
            
            .el-card {  
                font-size: 20px;
                margin-bottom: 10px;
                background: transparent;
                color: hsla(160, 100%, 37%, 1);
                border-color: rgba(0, 189, 126, 0.589);
            }
            .el-card:hover{
                border-color: aqua;
            }


        }
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
        :deep(.el-textarea__inner){
            color: hsla(160, 100%, 37%, 1);
            background-color: transparent;
        }
        .header {
            font-weight: 700;
            margin-bottom: 10px;
        }

        .bodyContent {
            height: auto;
            width: 100%;
            margin-bottom: 30px;
            padding: 10px; /* 可选：添加内边距以增加内容与边框之间的间距 */
            border: 1px solid rgba(0, 189, 126, 0.589); /* 添加灰色边框 */
        }

        .date {
            display: flex;
            justify-content: space-between;
        }

        .bodyButtons {
            .el-button {
                color: hsla(160, 100%, 37%, 1);
            }
            .el-button:hover {
                color: rgb(104, 255, 205);
            }
        }
    }
</style>