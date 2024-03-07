<!-- 主框架 -->
<template>
  <el-container>
    <el-aside width="200px">
      <div class="logo-box">
        <span class="systemName">{{ systemName }}</span>
      </div>
      <!-- 侧边栏 -->
      <Menu></Menu>
    </el-aside>
    <el-container>
      <el-header>
        <!-- 头部 -->
        <el-breadcrumb>
          <el-breadcrumb-item><a href="/" class="home">首页</a></el-breadcrumb-item>
          <el-breadcrumb-item v-for="item in $store.state.tabs.breadcrumb" :key="item">{{ item }}</el-breadcrumb-item>
        </el-breadcrumb>
        <el-popover
          placement="top-start"
          width="200"
          trigger="hover">
          <div>
            <div class="role">
              <el-row>
                <el-col :span="24"><span>角色：</span></el-col>
              </el-row>
              <template v-if="$store.state.user.userInfo.authorize.roles.length > 0">
                <el-tag v-for="role in $store.state.user.userInfo.authorize.roles" :key="role.id">{{ role.describe }}</el-tag>
              </template>
              <el-tag v-else type="warning">没有分配任何角色</el-tag>
            </div>
            <el-button type="text" @click="changePassword">修改密码</el-button>
            <el-button type="text" @click="logout">退出登录</el-button>
          </div>
          <span class="welcome" slot="reference">欢迎您，{{ $store.state.user.userInfo.userName }}</span>
        </el-popover>
      </el-header>
      <el-main>
        <!-- 内容主体 -->
        <el-tabs 
          v-model="$store.state.tabs.activityTabs.name" 
          type="border-card" 
          @tab-click="tabClick"
          @tab-remove="tabRemove">
          <el-tab-pane
            v-for="(item, index) in $store.state.tabs.tabsList"
            :closable="item.closable"
            :name="item.name"
            :key="item.name">
            <template #label>
              <span style="font-size: 13px;">{{ item.name }}</span>
              <i style="margin-left: 3px;" v-if="item.name === $store.state.tabs.activityTabs.name" class="el-icon-refresh" @click.stop="refreshPage"></i>
            </template>
          </el-tab-pane>
        </el-tabs>
        <KeepAlive>
          <router-view :key="$route.fullPath"></router-view>
        </KeepAlive>
      </el-main>
    </el-container>
  </el-container>
</template>
  
<script>
import { fetch } from '../config';
import { platform } from '@/config/platform';
import tabsTool from '@/utils/tabsTool';

export default {
  data() {
    return {
      systemName: platform.systemName,
    }
  },
  mounted() {

  },
  methods: {
    tabClick(tab) {
      tabsTool.selectTab(tab.name);
    },
    tabRemove(name) {
      tabsTool.closeTab(name);
    },

    logout() {
      fetch.logout().then((res) => {
        if(res.code == 1) {
          tabsTool.closeAllTab();
          this.$router.push({ path: '/login' });
          this.$store.commit('CLEAR_USER_INFO');
        }
      });
    },
    changePassword() {
      tabsTool.openTab({ 
        name: `修改密码`, 
        url: `/main/changePassword` 
      });
    },

    refreshPage() {
      tabsTool.refreshCurrentTab();
    }
  }
}
</script>
  
<style lang="less" scoped>
  .el-container {
    height: 100%;
    padding: 0px;
    margin: 0px;
    overflow: hidden;
  }

  .el-aside {
    border-right: 1px solid #E4E7ED;
    overflow: hidden;

    .logo-box {
      height: 60px;
      background-color: #409EFF;
      display: flex;
      justify-content: center;

      .systemName {
        color: #fff;
        font-size: 20px;
        line-height: 60px;
        display: inline-block;
      }

    }

  }
  

  .el-main {
    // background-color: #f1f1f2;
    color: #333;
    overflow-y: scroll;
    padding: 0;

    .el-tabs {
      user-select: none;
      box-shadow: none;
      border: none;

      ::v-deep .el-tabs__content {
        display: none;
      }

    }

  }

  .el-header {
    background-color: #409EFF;
    display: flex;
    align-items: center;
    justify-content: space-between;
    // flex-direction: row-reverse;
    color: #fff;

    .systemName {
      font-weight: bold;
      font-size: 1.6rem;
    }

    .el-breadcrumb .home {
      color: #ffffff;
      font-weight:400 !important;
    }

    .el-breadcrumb ::v-deep .el-breadcrumb__inner {
      color: #ffffff !important;
      font-weight:400 !important;
    }
    /* 被选中时的颜色 */
    .el-breadcrumb__item:last-child ::v-deep .el-breadcrumb__inner {
      color: #ffffff !important;
      font-weight:800 !important;
    }

  }

  /deep/ .el-tabs__item:focus.is-active.is-focus:not(:active) {
    -webkit-box-shadow: none;
    box-shadow: none;
  }
  /deep/ .el-tag {
    margin: 3px !important;
  }
</style>