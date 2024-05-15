<template>
    <div class="main">
        <div class="content">
            <h2>This is a web-app used for postcards</h2>
            <br />
            <h3>What it contains :</h3>
            <p>1. ADD cards</p>
            <p>2. DELETE cards</p>
            <p>3. EDIT cards</p>
            <br />
            <h3>How it's built :</h3>
            <p>Based on Vue.js and Spring Boot</p>
            <br />
            <h3>Enjoy yourself</h3>
        </div>
    </div>
</template>
  
<style lang="less" scoped>

    .main {
        position: relative;
        height: 80vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .content {
        font-family: 'Roboto', sans-serif;
        font-size: 24px;
        line-height: 32px;
        text-align: center;
        color: hsla(160, 100%, 37%, 1);
        cursor: default;
        animation: fadeIn 1s ease-out;
    }

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

    h2, h3 {
        font-weight: 700;
        margin-bottom: 10px;
    }

    p {
        margin-bottom: 5px;
    }
</style>
