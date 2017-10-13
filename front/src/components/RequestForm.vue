
<template>
	<form method="post">
	<div class="container col-md-10 offset-md-1 wrapper mb-5 col-lg-10">
		<div class="container col-md-10 mt-5">
			<div class="row">
				<h1 class="text-center form-heading">Security Service Request</h1>
			</div>
			<div class="row">
				<div class="form-group col-md-4">
					<label for="requested-by-input">Requested By</label>
					<input type="text" id="requested-by-input" class="form-control"/>
				</div>
				<div class="form-group col-md-4">
					<label for="department-input">Department</label>
					<input type="text" id="department-input" class="form-control"/>
				</div>
				<div class="form-group col-md-4">
					<label for="sfu-id-or-bcdl-input">SFU ID or BCDL</label>
					<input type="text" id="sfu-id-or-bcdl-input" class="form-control"/>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-4">
					<label for="phone-input">Phone</label>
					<input type="text" id="phone-input" class="form-control"/>
				</div>
				<div class="form-group col-md-4">
					<label for="email-input">Email</label>
					<input type="email" id="email-input" class="form-control"/>
				</div>
				<div class="form-group col-md-4">
					<label for="fax-input">Fax (optional)</label>
					<input type="text" id="fax-input" class="form-control"/>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-4">
					<label for="type-name-of-event-input">Type/Name of Event</label>
					<input type="text" id="type-name-of-event-input" class="form-control"/>
				</div>
				<div class="form-group col-md-4">
					<label for="location-input">Location</label>
					<input type="text" id="location-input" class="form-control"/>
				</div>
				<div class="form-group col-md-4">
					<label for="number-of-attendees-input"># of Attendees</label>
					<input type="text" id="number-of-attendees-input" class="form-control"/>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-12">
					<label for="details-input" placeholder="">Details</label>
					<input type="textbox" id="details-input" class="form-control"/>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-12">
					<input type="checkbox" id="licensed-input" class="mr-1 mt-4"/>
					<label for="licensed-input">Licensed</label>
				</div>
			</div>
			<div class="row mt-4">
				<table class="table table-striped">
					<thead>
					<tr>
						<th>Date</th>
						<th>Time</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td>
							<input type="text" id="date" class="form-control"/>
						</td>
						<td>
							<input type="text" id="time" class="form-control"/>
						</td>
					</tr>
					</tbody>
				</table>
				<div class="row mt-4">
					<i class="fa fa-calendar calendar-icon mr-3"></i>
					<button class="btn btn-danger">Add Date</button>
				</div>
			</div>
			<div>
				<h3 class="auth-heading mt-4">Authorization</h3>
			</div>
			<div class="row mt-5">
				<div class="form-group col-md-4">
					<label for="account-code-input">Account code</label>
					<input type="text" id="account-code-input" class="form-control"/>
				</div>
				<div class="form-group col-md-4">
					<label for="name-of-authorizee-input">Authorized by</label>
					<input type="text" id="name-of-authorizee-input" class="form-control"/>
				</div>
				<div class="form-group col-md-4">
					<label for="sfu-id-or-bcdl-of-authorizee-input">SFU ID or BCDL of Authorizee</label>
					<input type="text" id="sfu-id-or-bcdl-of-authorizee-input" class="form-control"/>
				</div>
				<div class="form-group col-md-4">
					<label for="date-of-authorization-input">Date</label>
					<input type="text" id="date-of-authorization-input" class="form-control"/>
				</div>
				<div class="form-group col-md-4">
					<label for="phone-of-authorizee-input">Phone</label>
					<input type="text" id="phone-of-authorizee-input" class="form-control"/>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-12">
					<input type="checkbox" id="invoice-input" class="mr-1"/>
					<label for="invoice-input">Please Invoice</label>
				</div>
			</div>
			<div class="row mt-5">
				<div class="col-md-12 text-center">
					<button class="btn btn-danger btn-lg col-md-8">Submit</button>
				</div>
			</div>
			<div class="bottom-space"></div>
		</div>
	</div>
</form>
</template>

<script>
export default {
  name: 'RequestForm',
  
}
</script>