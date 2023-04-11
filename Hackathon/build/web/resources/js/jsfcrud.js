function handleSubmit(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
    }
}

function addPatrimonio(){
    window.location="./IncluirPat.xhtml";
}

function addFuncionario(){
    window.location="./Incluir.xhtml";
}

function editPatrimonio(){
    window.location="./EditarPat.xhtml";
}

function editFuncionario(){
    window.location="./Editar.xhtml";
}

function irListar(){
    window.location="./Listar.xhtml";
}

function irListarPat(){
    window.location="./ListarPat.xhtml";
}

