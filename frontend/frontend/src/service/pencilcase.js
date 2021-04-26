


class PencilCase{

    getNameNCountry(city, country) {
        return fetch("https://localhost:8080/?city=" + city + "&country=" + country
        ,{
            method: 'GET',
            mode: 'cors'
        });
    }    


}


export default new PencilCase();
