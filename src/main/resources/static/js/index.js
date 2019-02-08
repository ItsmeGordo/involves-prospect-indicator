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
        shops: [],
        selectedEmployee: null
      }
    },
    methods: {
        getEmployees: function() {
             axios.get('/api/employee/getEmployees').then(response => {
                 this.items = response.data;
             });
         },
        getShopsByEmployee: function(item) {
            this.selectedEmployee = item;
            if (this.selectedEmployee != null) {
                var params = {params: {name: this.selectedEmployee.name, employee_lat: this.selectedEmployee.latitude, employee_log: this.selectedEmployee.longitude, radius:2}};
                axios.get('/api/shop/getShopsInRadius', params).then(response => {
                    this.shops = response.data;
                });
            }
        },
        downloadShopsList: function() {
            if (this.selectedEmployee != null) {
                var params = {params: {name: this.selectedEmployee.name, employee_lat: this.selectedEmployee.latitude, employee_log: this.selectedEmployee.longitude, radius:2}};
                axios.get('/api/shop/downloadShopsInRadius', params).then(response => {
                    const url = window.URL.createObjectURL(new Blob([response.data]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', this.selectedEmployee.name + '.csv');
                    document.body.appendChild(link);
                    link.click();
                });
            }
        },
        downloadBestRoute: function() {
            if (this.selectedEmployee != null) {
                var params = {params: {name: this.selectedEmployee.name, employee_lat: this.selectedEmployee.latitude, employee_log: this.selectedEmployee.longitude, radius:2}};
                axios.get('/api/shop/downloadBestRoute', params).then(response => {
                    const url = window.URL.createObjectURL(new Blob([response.data]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', this.selectedEmployee.name + '_best_route.csv');
                    document.body.appendChild(link);
                    link.click();
                });
            }
        }
    },
    mounted () {
        this.getEmployees();
    }
})