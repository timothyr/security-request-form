<!-- match style from /security -->
<style>
  body {
    background-color: #ededed;
  }
</style>

<template>
  <div class="container col-md-10 offset-md-1 wrapper mb-5 col-lg-10">
    <div class="container col-md-10">
      <div class="row mt-5" >
        <div class="container mt-4"></div>
        <h1 class="mt-5">Service #{{requestID}}</h1>
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
          dates: [],
          times: [],
          hours: [],
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
        this.$data.email = "smithysmithers@sfu.ca"
        this.$data.location = "Surrey";
        this.$data.eventName = "Beer Pong";
        this.$data.numAttendees = 400;
        this.$data.details = "Biggest thing ever";
        this.$data.dates = ["October 26, 2017", "October 27, 2017"];
        this.$data.times = ["7:00pm - 2:00am", "8:30pm - 1:00am"];
        this.$data.hours = [7, 4.5];
        this.$data.author = "SFSS@sfu.ca";
        this.$data.authorID = 1234567;
        this.$data.accountCode = "546-546546-5453131";
        this.$data.authorPhone = "911";
        this.$data.authorInvoice = "yes";

        console.log(this.$data.requestedBy);
      }
    },

    created() {
      console.log(this.$route.params.id);
      this.loadDummyData();

      // TODO: load real data
      //$.get("http://localhost:8080/api/form/get/"+(this.$route.params.id));
    },

  }
</script>
