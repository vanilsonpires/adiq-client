const autenticar = () =>{
    generateToken();
}

const addresApi = () => $("#address").val();

const username = ()=> $("#username").val();

const password = ()=> $("#password").val();

const clientid = ()=> $("#clientid").val();

const feedback = (message) => $("#messager").html(message);

const clientpassword = ()=> $("#clinetpassword").val();

const loginSucess = (data) =>{
    $("#stats").html('Authenticated');
    $("#token").html(data.access_token);    
    feedback('Login successful');
}

const basicAuthorization = (request) =>  request.setRequestHeader("Authorization", "Basic " + btoa(clientid() + ":" + clientpassword()));

const formData = (formid) =>{
    let array = $(`#${formid}`).serializeArray();
    let data = {};
    array.forEach((obj)=>{
        data[obj.name] = obj.value
    });
    return data;
}

const uuidv4 = () => Math.random().toString(36).slice(-6);

const addItem = (item) =>{
    let fakeId = uuidv4();
    $('#cart tr:last').after(`<tr id="carRow${fakeId}">
        <td>${fakeId}</td>    
        <td>${item.description}</td>
        <td class="price">${item.price}</td>
        <td><button class="btn btn-danger" onClick="removeItem('${fakeId}')">Remover</button></td>
    </tr>`);
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

$(window).on("load", function(){
    $("#formProduct").submit((event)=>{
        event.preventDefault();
        addItem(formData('formProduct'));
        $('#formProduct').trigger("reset");
        $("#description").focus();
    })
 })