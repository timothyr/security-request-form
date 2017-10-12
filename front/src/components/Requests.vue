<style>
	body {
		background-color: #ededed;
	}
</style>

<template>
	<div class="container col-md-10 offset-md-1 wrapper mb-5 col-lg-10">
		<div class="container col-md-10">
			<div class="row mt-5">
				<h1 class="mt-5">Requests</h1>
			</div>
			<div class="row mt-5">
				<div class="input-group search-group">
					<span class="fa fa-search"></span>
					<input type="text" v-model="searchTerm" class="form-control" id="search-bar" placeholder="search"/>
					<div class="error"></div>
				</div>
			</div>
			<div class="row mt-3">
				<div class="col-md-8 mx-auto">
					<ul class="nav nav-pills nav-fill">
						<li class="nav-item"><a href="" v-on:click.prevent="greet" class="nav-link active">All</a></li>
						<li class="nav-item"><a href="" class="nav-link">Waiting</a></li>
						<li class="nav-item"><a href="" class="nav-link">Approved</a></li>
						<li class="nav-item"><a href="" class="nav-link">Rejected</a></li>
					</ul>
				</div>
			</div>
			<div class="row mt-4">
				<table class="table">
					<thead>
						<tr>
							<th>Name<i class="fa fa-sort fa-fw pull-right"></i></th>
							<th>Phone<i class="fa fa-sort fa-fw pull-right"></i></th>
							<th>ID<i class="fa fa-sort fa-fw pull-right"></i></th>
							<th>Location<i class="fa fa-sort fa-fw pull-right"></i></th>
							<th># Attendees<i class="fa fa-sort fa-fw pull-right"></i></th>
						</tr>
					</thead>
					<tbody>
					<tr v-for="request in requests">
						<td>{{request.name}}</td>
						<td>{{request.phone}}</td>
						<td>{{request.id}}</td>
						<td>{{request.location}}</td>
						<td>{{request.numAttendees}}</td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="bottom-space"></div>
	</div>
</template>

<script>
export default {
  name: 'Requests',
  data() {
  	return {
		requests: [
		{
			name: "Ray",
			phone: "911",
			id: "301235622",
			location: "Surrey",
			numAttendees: 5
		}
		],
		searchTerm: ""
  	}
  },
  methods: {
	greet: function(e){ console.log(e); console.log("hello");}
  },
  computed: {
	
  }
}
</script>