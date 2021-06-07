const autenticar = () => generateToken();

const addresApi = () => $("#address").val();

const username = ()=> $("#username").val();

const password = ()=> $("#password").val();

const clientid = ()=> $("#clientid").val();

const token = ()=> $("#token").html();

const feedback = (message) => $("#messager").html(message);

const clientpassword = ()=> $("#clinetpassword").val();

const uuidv4 = () => Math.random().toString(36).slice(-6);

const basicAuthorization = (request) =>  request.setRequestHeader("Authorization", "Basic " + btoa(clientid() + ":" + clientpassword()));

const bearerAuthorization = (request) =>  request.setRequestHeader("Authorization", `bearer ${token()}`);

const loginSucess = (data) =>{
    $("#stats").html('Authenticated');
    $("#token").html(data.access_token);    
    feedback('Login successful');
}

const formData = (formid) =>{
    let array = $(`#${formid}`).serializeArray();
    let data = {};
    array.forEach((obj)=>{
        data[obj.name] = obj.value
    });
    return data;
}

const addItem = (item) =>{
    let fakeId = uuidv4();
    $('#cart tr:last').after(`
        <tr id="carRow${fakeId}">
            <td>${fakeId}</td>    
            <td class="description">${item.description}</td>
            <td class="price">${item.price}</td>
            <td>
                <button class="btn btn-danger" onClick="removeItem('${fakeId}')">
                    Remover
                </button>
            </td>
        </tr>`
    );
    calcSum();
}

const calcSum = ()=>{
    let sum = 0.0;
    $('#cart .price').each((index, element)=>{
        sum = sum + parseFloat($(element).html());
    })
    $("#sum").val(sum);
}

const removeItem = (row) =>{
    $(`#carRow${row}`).remove();
    calcSum();
}

const generateToken = ()=>{
    let address = addresApi();
    let data = {
        grant_type: 'password',
        username: username(),
        password: password()
    }
    $.ajax({
        type: "POST",
        url: `${address}/auth/oauth/token`,
        beforeSend: basicAuthorization,
        data: data,        
        error: (response) => {
            feedback(response.responseText);
            $("#stats").html('no autenticated');
            $("#token").html('no token');    
        },
        success: (response) => loginSucess(response)
      });
}

const toJsonProducts = ()=>{
    let items = [];
    $('#cart .price').each((index, element)=>{
        items[index] = {
            amount: parseFloat($(element).html()),
            ratePercent: 0,
            rateAmount: 0,
            description: ''
        }
    });
    $('#cart .description').each((index, element)=>{
        items[index].description = $(element).html();
    });    
    return items;
}

const pay = (values)=>{
    let address = addresApi();
    let data = {
        transactionType: values.typePgt,
        amount: parseFloat(values.sum),
        currencyCode: 'brl',
        productType: "avista",
        installments: values.installments,
        captureType: "ac",
        recurrent: false ,
        cardInfo: {
            numberToken: values.cardNumber,
            cardholderName: values.owner,
            securityCode: values.securityCode,
            brand: values.brand,
            expirationMonth: values.expirationMonth,
            expirationYear: values.expirationYear
        },
        sellers: [{
            amount: values.sum,
            items: toJsonProducts()
        }]
    }
    $.ajax({
        type: "POST",
        url: `${address}/payments`,
        contentType: "application/json",
        dataType : 'json',
        beforeSend: bearerAuthorization,
        data: JSON.stringify(data),        
        error: (response) => {
            feedback(response.responseText);
        },
        success: (response) => feedback('Compra efetuada com sucesso')
      });
}

$(window).on("load", function(){
    $("#formProduct").submit((event)=>{
        event.preventDefault();
        addItem(formData('formProduct'));
        $('#formProduct').trigger("reset");
        $("#description").focus();
    })

    $("#formPgt").submit((event)=>{
        event.preventDefault();
        pay(formData("formPgt"));
    })

    $.get("https://api.ipify.org/?format=json",(response)=>{
        $("#myip").val(response.ip);
    })
 });
