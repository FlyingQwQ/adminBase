<template>
    <div class="login">
        <div class="frame">
            <el-row :gutter="20">
                <el-col :span="24"><span class="title">用户登录</span></el-col>
            </el-row>

            <el-form :model="loginForm" :rules="rules" ref="ruleForm">
                <el-form-item label="用户名" prop="name">
                    <el-input v-model="loginForm.name"></el-input>
                </el-form-item>

                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="loginForm.password"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-row>
                        <el-col :span="24">
                            <el-button type="primary" @click="login">登录</el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
            </el-form>

        </div>
    </div>
</template>

<script>
import { fetch } from '@/config'

export default {
    data() {
        return {
            loginForm: {
                name: '',
                password: ''
            },
            rules: {
                name: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
            }
        }
    },
    mounted() {

    },
    methods: {
        login() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    fetch.login({
                        username: this.loginForm.name,
                        password: this.loginForm.password
                    }).then((res) => {
                        if(res.code == 1) {
                            localStorage.setItem('userInfo', JSON.stringify(res.data));
                            this.$store.commit('SET_USER_INFO', res.data);
                            this.$router.push('/main/');
                        } else {
                            this.$message.error(res.msg);
                        }
                    }).catch((errorMsg) => {
                        this.$message.error(errorMsg);
                    });
                } else {
                    return false;
                }
            });
        }
    }
}
</script>
  
<style lang="less" scoped>

    .login {
        height: 100vh;
        width: 100vw;
        background-color: #eeeeee;
    }

    .frame {
        background-color: white;
        min-width: 500px;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        border-radius: 5px;
        padding: 50px;
        box-sizing: border-box;

        .title {
            font-size: 24px;
            color: #2b78f0;
        }

        .el-form {
            margin-top: 40px;

            .el-button {
                width: 100%;
            }

        }
        

    }
</style>
  