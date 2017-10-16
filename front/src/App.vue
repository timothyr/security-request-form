<template>
  <div id="app">
    <AppHeader/>
    <router-view></router-view>
  </div>
</template>

<script>
import AppHeader from './components/AppHeader'
import RequestForm from './components/RequestForm'

export default {
  name: 'app',
  components: {
    AppHeader,
    RequestForm
  }
}
</script>

<style>
</style>
