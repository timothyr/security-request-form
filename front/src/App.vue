<template>
  <div id="app">
    <AppHeader/>
    <RequestForm/>
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
