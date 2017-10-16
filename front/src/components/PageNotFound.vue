<template>
	<div class="container col-md-10 wrapper mt-5">
		<div class="row">
			<i class="fa fa-exclamation-circle fa-5x mx-auto mt-5 mb-4"></i>
		</div>
		<div class="row">
			<h1 class="mb-2 mt-2 mx-auto">404. Page Not Found</h1>
		</div>
		<div class="row">
			<span class="text-muted mx-auto">We could not find {{fullPath}} you've requested</span>
		</div>
		<div class="bottom-space"></div>
	</div>
</template>

<script>

export default {
  name: 'PageNotFound',
  data() {
  	return {
  		fullPath: ""
  	}
  },
  created: function() {
  	this.fullPath = this.$route.fullPath;
  }
}
</script>