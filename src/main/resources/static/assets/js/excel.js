let selectedFile;
console.log(window.XLSX);
document.getElementById('formFile').addEventListener("change", (event) => {
    selectedFile = event.target.files[0];
})

let data=[{
    "name":"jayanth",
    "data":"scd",
    "abc":"sdef"
}]
document.getElementById('excelSubmitButton').addEventListener("click", () => {
    let formSection = $('.form-block');
    formSection.block({
        message:
            '<div class="spinner-border text-primary" role="status"></div>',
        css: {
            backgroundColor: 'transparent',
            border: '0'
        },
        overlayCSS: {
            backgroundColor: '#fff',
            opacity: 0.8
        }
    });

    XLSX.utils.json_to_sheet(data, 'out.xlsx');
    if(selectedFile){
        let fileReader = new FileReader();
        fileReader.readAsBinaryString(selectedFile);
        fileReader.onload = (event)=>{
         let data = event.target.result;
         let id_departement = document.getElementById("id_deparetement").value;
         let workbook = XLSX.read(data,{type:"binary"});
         console.log(workbook);
         
         ////////////////////////////////////
         workbook.SheetNames.forEach(sheet => {
            let rowObject = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheet]);
            // transform object 
            rowObject.forEach(member => {

                if(member.laboratoire) member.type = "prof";
                else member.type = "membre";

                let role = (member.role == "chef de departement")? "CHEF_DEP":"ADMIN";
                role = [{roleName:role}];
                let compte = {username:member.username,password:member.password,email:member.email,role:role};
                member.compte = compte;
                delete member.username;
                delete member.password;
                delete member.email;
                delete member.role;
                member.departement = {id_departement:id_departement};
            });
            console.log(rowObject)
            // end transform object
             $.ajax({
                 url: "/addExcel",
                 dataType: 'text',
                 type: 'post',
                 contentType: 'application/json',
                 data: JSON.stringify(rowObject),
                 processData: false,
                 success: function( data, textStatus, jQxhr ){
                     formSection.unblock();
                     Swal.fire({
                         icon: 'success',
                         title: 'successé!',
                         text: 'la list des membres est bien enregistrer.',
                         customClass: {
                             confirmButton: 'btn btn-success'
                         }
                     }).then(function (result) {
                         window.location.reload();
                     });
                 },
                 error: function( jqXhr, textStatus, errorThrown ){
                     formSection.unblock();
                     toastr['error']('l\'enregistrement a echoué.', 'Echec!', {
                         closeButton: true,
                         tapToDismiss: false,
                         rtl: false
                     });
                 }
             });
        });
        ////////////////////////////////////
       
        }
    }
});