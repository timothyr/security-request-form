<!-- match style from /security -->
<style>
  body {
    background-color: #ededed;
  }

</style>

<template>
  <div class="container col-md-10 offset-md-1 wrapper mb-5 col-lg-10">
    <div class="container col-md-10">
      <div class="row mt-5">
        <div class="container mt-4"></div>
        <h3 class="mt-5 mx-5">Service #{{requestID}}</h3>
      </div>
      <div class="row mt-3">
        <div class="col">
          <p class="mx-5">Status: {{status}}</p>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <p class="mx-5">Requested By: {{requestedBy}}</p>
        </div>
        <div class="col">
          <p class="mx-5">SFU ID: {{SFUID}}</p>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <p class="mx-5">Phone: {{phone}}</p>
        </div>
        <div class="col">
          <p class="mx-5">Fax: {{fax}}</p>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <p class="mx-5">Email: {{email}}</p>
        </div>
        <div class="col">
          <p class="mx-5">Location: {{location}}</p>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <p class="mx-5">Type/Name of Event: {{eventName}}</p>
        </div>
        <div class="col">
          <p class="mx-5"># Attendees: {{numAttendees}}</p>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <p class="mx-5">Details: {{details}}</p>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <h5 class="mt-2 text-center">Licensed: {{licensed}}</h5>
        </div>
      </div>

      <div class="row my-4">
        <table class="table">
          <thead>
            <tr>
              <th class="text-center">Date</th>
              <th class="text-center">Time</th>
              <th class="text-center">Hours</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="d in dates">
              <td class="text-center">{{d.date}}</td>
              <td class="text-center">{{d.time}}</td>
              <td class="text-center">{{d.hours}}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="row">
        <div class="col">
          <p class="mx-5">Authorized by: {{author}}</p>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <p class="mx-5">Account Code: {{accountCode}}</p>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <p class="mx-5">SFU ID of Authorizee: {{authorID}}</p>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <p class="mx-5">Phone: {{authorPhone}}</p>
        </div>
        <div class="col">
          <p class="mx-5">Invoice: {{authorInvoice}}</p>
        </div>
      </div>

      <!-- TODO: Security inputs -->
      <div class="row mb-3">
        <div class="col col-md-3"></div>
        <div class="col col-md-6 align-content-center">
          <button class="btn">Reject</button>
          <button class="btn">Accept</button>
          <button class="btn">Update</button>
        </div>
        <div class="col col-md-3"></div>
      </div>

    </div>
  </div>
</template>

<script>
  export default {
    name: 'SecurityView',

    data() {
      return {
          requestID: "",
          status: "",
          requestedBy: "",
          SFUID: "",
          phone: "",
          fax: "",
          email: "",
          location: "",
          eventName: "",
          numAttendees: "",
          details: "",
          licensed: "",
          dates: [{
            date: "",
            time: "",
            hours: "",
          }],
          author: "",
          accountCode: "",
          authorID: "",
          authorPhone: "",
          authorInvoice: ""
      }
    },

    methods: {
      loadDummyData: function() {
        this.$data.requestID = this.$route.params.id;
        this.$data.requestedBy = "Steve Smith";
        this.$data.SFUID = 301254522;
        this.$data.status = "Waiting";
        this.$data.phone = "604-430-4444";
        this.$data.fax = "(04) 3056 6523";
        this.$data.email = "smithysmithers@sfu.ca";
        this.$data.location = "Surrey";
        this.$data.eventName = "Beer Pong";
        this.$data.numAttendees = 400;
        this.$data.details = "Biggest thing ever";
        this.$data.licensed = "No";
        this.$data.dates = [
          {
            date: "Aug 22, 2017",
            time: "7:00pm - 2:00am",
            hours: 7
          },
          {
            date: "Aug 26, 2017",
            time: "8:00pm - 1:30am",
            hours: 5.5
          }];
        this.$data.author = "SFSS@sfu.ca";
        this.$data.authorID = 1234567;
        this.$data.accountCode = "546-546546-5453131";
        this.$data.authorPhone = "911";
        this.$data.authorInvoice = "yes";

        console.log(this.$data.requestedBy);
        console.log(this.$data.dates[0].date);
      }
    },

    created() {
      console.log(this.$route.params.id);
      this.loadDummyData();

      // TODO: load real data, 404 if form does not exist
      /*$.get("http://localhost:8090/api/form/get/"+(this.$route.params.id), function(data) {

        });
      */
    },

  }
</script>
