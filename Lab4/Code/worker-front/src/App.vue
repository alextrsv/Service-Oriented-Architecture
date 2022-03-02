<template>
  <div id ="app">
    <h1>Workers list</h1>
    <hr/>
    <WorkersList
        v-bind:workers="workers"
        v-bind:message="message"
        @remove-worker="removeWorker"
        @show-worker-info="showWorkerInfo"
        @get-workers="getWorkers"
        @add-workers="addWorker"
        @upd-workers="updateWorker"
        @get-max-pos="getMaxPos"
        @get-amount-by-date="getAmountByDate"
        @get-amount-by-status="getAmountByStatus"
        @fire-workers="fireWorker"
        @hire-workers="hireWorker"
    />
    <WorkerView
        v-bind:cworker="currentWorker"
    />
  </div>
</template>

<script>
import WorkersList from "@/components/WorkersList";
import WorkerView from "@/components/WorkerView";

export default {
  name: 'app',
  components: {
    WorkerView,
    WorkersList,
    // OrganizationView
  },
  data() {
    return{
      workers: [],
      currentWorker: {},
      page: 0,
      size: 2,
      url : 'http://localhost:8081/api/workers',
      finalUrl: '',
      params : [''],
      filterParams : [''],
      sortParams : '',
      newWorker: {},
      updWorker: {},
      message : {},
      status: ''
    }
  },

  mounted() {
    this.getWorkers(0,0, null, null, '')
  },
  methods: {
    removeWorker() {
      console.log(this.currentWorker.id)
      this.finalUrl = this.url + '/' + this.currentWorker.id
      fetch(this.finalUrl, {
        method: 'DELETE'})
          .then(res => {
            if (!res.ok){
              res.json().then(json => this.message = json.detailMessage)
            }
            else {
              res.json().then(json=> this.newWorker = json)
              this.message = 'deleted successfully'
            }
          })
    },

    showWorkerInfo(worker){
      console.log('show info about: '.concat(worker.name))
      this.currentWorker = worker;
    },

    updateWorker(paramAmount, params){
      this.finalUrl = this.url
      if (paramAmount > 0) {
        this.params = []
        this.params = Object.assign(params);
        // this.finalUrl += this.addParamsToUrl()


        let urlAdds = '?'
        let det = '&'
        for (let i = 0; i < this.params.length; i++) { // выведет 0, затем 1, затем 2
          if (i === this.params.length - 1) det ='';
          urlAdds += this.params[i] + det
        }
        this.finalUrl += urlAdds

      }
      fetch(this.finalUrl, {
        method: 'PUT'})
          .then(res => {
            if (!res.ok){
              res.json().then(json => this.message = json.detailMessage)
            }
            else {
              res.json().then(json=> this.updWorker = json)
              this.updWorker = res.json()
            }
          })


      fetch(this.url)
          .then(response => response.json())
          .then(json =>{
            this.workers = json
          })
    },

    addWorker(paramAmount, params){
      this.finalUrl = this.url
      if (paramAmount > 0) {
        this.params = []
        this.params = Object.assign(params);
        this.finalUrl += this.addParamsToUrl()
      }
      console.log(this.finalUrl)
      fetch(this.finalUrl, {
        method: 'POST'})
          .then(res => {
            if (!res.ok){
              res.json().then(json => this.message = json.detailMessage)
            }
            else {
              res.json().then(json=> this.newWorker = json)
            }
          })

      fetch(this.url)
          .then(response => response.json())
          .then(json =>{
            this.workers = json
          })
    },

    getWorkers(paramAmount, filterParamAmount, params, filterParams, sortParam){
      this.finalUrl = this.url
      //add params
      if (paramAmount > 0) {
        if(paramAmount === 1) this.finalUrl += params[0]
        else {
          this.params = []
          this.params = Object.assign(params);
          this.finalUrl += this.addParamsToUrl()
        }
      }
      //add filter params
      if (filterParamAmount > 0){
        this.filterParams = []
        this.filterParams = Object.assign(filterParams);
        this.finalUrl += this.addFilterParamsToUrl()
      }

      //add sort params
      if (sortParam !== '')  this.finalUrl += '&' + sortParam;

      console.log(this.finalUrl)

      fetch(this.finalUrl)
          .then(res => {
            if (!res.ok){
              res.json().then(json => this.message = json.detailMessage)
            }
            else {
              res.json().then(json=> this.workers = json)
              // this.message = 'new worker was added: '  + this.newWorker.name
            }
          })
    },

    addParamsToUrl(){
      let urlAdds = '?'
      let det = '&'
      for (let i = 0; i < this.params.length; i++) { // выведет 0, затем 1, затем 2
        if(this.params[i].includes('id')) {
          urlAdds = '/' + this.params[i].substr(3,this.params[i].length) + '?'
          continue
        }
        if (i === this.params.length - 1) det ='';
        urlAdds += this.params[i] + det
      }
      return urlAdds
    },

    addFilterParamsToUrl(){
      let urlAdds = ''
      if (this.params.length === 0) urlAdds = '?filter='
      else urlAdds = '&filter='
      let det = ';'
      for (let i = 0; i < this.filterParams.length; i++) { // выведет 0, затем 1, затем 2
        if (i === this.filterParams.length - 1) det ='';
        urlAdds += this.filterParams[i] + det
      }
      return urlAdds
    },

    getMaxPos(){
      fetch("http://localhost:8081/api/workers/filter/maxPos")
          .then(res => {
            if (!res.ok){
              res.json().then(json => this.message = json.detailMessage)
            }
            else {
              res.json().then(json=> this.workers = json)
              // this.message = 'new worker was added: '  + this.newWorker.name
            }
          })
    },

    getAmountByDate(filterParams){
      this.filterParams = []
      this.filterParams = Object.assign(filterParams);
      let dateAmountUrl =  'http://localhost:8081/api/workers/date/amount?' + filterParams[0]

      console.log(dateAmountUrl)

      fetch(dateAmountUrl)
          .then(res => {
            if (!res.ok){
              res.json().then(json => this.message = json.detailMessage)
            }
            else {
              res.json().then(json=> this.message = json)
              // this.message = 'new worker was added: '  + this.newWorker.name
            }
          })
    },
    getAmountByStatus(filterParams){
      this.filterParams = []
      this.filterParams = Object.assign(filterParams);
      let dateAmountUrl =  'http://localhost:8081/api/workers/status/amount?' + filterParams[0]

      console.log(dateAmountUrl)

      fetch(dateAmountUrl)
          .then(res => {
            if (!res.ok){
              res.json().then(json => this.message = json.detailMessage)
            }
            else {
              res.json().then(json=> this.message = json)
              // this.message = 'new worker was added: '  + this.newWorker.name
            }
          })
    },

    fireWorker(workerid){
      let fireUrl = 'http://localhost:8082/worker/fire/' + workerid;
      console.log(fireUrl)

      fetch(fireUrl, {
        method: 'POST'})
          .then(res => {
            if (!res.ok){
              res.json().then(json => this.message = json.detailMessage)
            }
            else {
            //   res.json().then(json=> this.message = json)
              this.message = 'worker was fired: '
            }
          })
    },

    hireWorker(uri){
      let hireUrl = 'http://localhost:8082/worker/' + uri;
      console.log(hireUrl)

      fetch(hireUrl, {
        method: 'POST'})
          .then(res => {
            if (!res.ok){
              res.json().then(json => this.message = json.detailMessage)
            }
            else {
              //   res.json().then(json=> this.message = json)
              this.message = 'worker was hired: ';
            }
          })

    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
