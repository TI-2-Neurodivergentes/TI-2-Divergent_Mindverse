// Obtenha o parâmetro da URL que determina qual conteúdo deve ser exibido
function getNeurodivergenciaParameter() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get("neurodivergencia");
}

function exibirConteudo() {
    const neurodivergencia = getNeurodivergenciaParameter();

    // Use as classes Bootstrap para mostrar/ocultar os conteúdos
    document.getElementById(neurodivergencia + "-content").classList.remove("d-none");
    document.getElementById(neurodivergencia + "-content").classList.add("d-block");
}

// Aguarde o carregamento da página e chame a função para exibir o conteúdo apropriado
document.addEventListener("DOMContentLoaded", exibirConteudo);
