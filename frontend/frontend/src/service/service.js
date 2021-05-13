


class Service{

    getNameNCountry(city, country) {
        return fetch("http://localhost:8080/airquality/?city=" + city + "&country=" + country
        ,{
            method: 'GET',
            mode: 'cors'
        });
    }    

    getId(id){
        return fetch("http://localhost:8080/airquality/?id=" + id, {
            method: 'GET',
            mode: 'cors'
        });
    }

    getCache(){
        return fetch("http://localhost:8080/cache", {
            method: 'GET',
            mode: 'cors'
        })
    }

}


export default new Service();
