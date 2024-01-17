<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        
      }
    },
    mounted() {
      let userInfoJson = JSON.parse(localStorage.getItem('userInfo') ?? '{}');
      if(!userInfoJson.token) {
        this.$router.push('/login');
      } else {
        this.$store.commit('SET_USER_INFO', userInfoJson);
        this.$router.push('/main/');
      }
    }
  }
</script>

<style>
 html, body, #app {
  height: 100%;
  padding: 0px;
  margin: 0px;
 }
</style>
