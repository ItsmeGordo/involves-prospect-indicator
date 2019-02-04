new Vue({
  el: '#app',
  data () {
      return {
        items: [
        ],
        right: '',
        shopHeaders: [
          {
            text: 'Nome da Loja',
            value: 'name',
            sortable: false
          },
          { text: 'Latitude', value: 'latitude', sortable: false},
          { text: 'Longitude', value: 'longitude', sortable: false },
          { text: 'Distance (KM)', value: 'carbs', sortable: false }
        ],
        shops: []
      }
    },
    methods: {
        load: function() {
             axios.get('/api/employee/getEmployees').then(response => {
                 this.items = response.data;
             });
         }
        },
    mounted () {
        this.load();
    }
})