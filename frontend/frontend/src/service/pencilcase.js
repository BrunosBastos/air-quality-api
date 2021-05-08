


class PencilCase{

    getNameNCountry(city, country) {
        return fetch("http://localhost:8080/?city=" + city + "&country=" + country
        ,{
            method: 'GET',
            mode: 'cors'
        });
    }    

    getId(id){
        return fetch("http://localhost:8080/?id=" + id, {
            method: 'GET',
            mode: 'cors'
        });
    }

}


export default new PencilCase();
