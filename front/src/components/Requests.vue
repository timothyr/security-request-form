<template>
	<div class="container col-md-10 offset-md-1 wrapper mb-5 col-lg-10">
		<div class="container col-md-10">
			<div class="row mt-5">
				<h1 class="mt-5">Requests</h1>
			</div>
			<div class="row mt-5">
				<div class="input-group search-group">
					<span class="fa fa-search"></span>
					<input type="text" class="form-control" id="search-bar" placeholder="search"/>
					<div class="error"></div>
				</div>
			</div>
			<div class="row mt-3">
				<div class="col-md-8 mx-auto">
					<ul class="nav nav-pills nav-fill">
						<li class="nav-item"><a href="#" class="nav-link active">All</a></li>
						<li class="nav-item"><a href="#" class="nav-link">Waiting</a></li>
						<li class="nav-item"><a href="#" class="nav-link">Approved</a></li>
						<li class="nav-item"><a href="#" class="nav-link">Rejected</a></li>
					</ul>
				</div>
			</div>
			<div class="row mt-4">
				<table class="table">
					<thead>
						<tr>
							<th>Name</th>
							<th>Phone</th>
							<th>ID</th>
							<th>Location</th>
							<th># Attendees</th>
						</tr>
					</thead>
					<tbody>
					<tr>
						<!--LIST OF REQUESTS HERE -->
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
  
}
</script>