const vatFormats: Record<string, RegExp> = {
    DE: /^DE[0-9]{9}$/,
    FR: /^FR[A-HJ-NP-Z0-9]{2}[0-9]{9}$/,
    DK: /^DK[0-9]{8}$/,
    AT: /^ATU[0-9]{8}$/,
    GB: /^GB[0-9]{9}$/,
    NL: /^NL[0-9]{9}B[0-9]{2}$/
};

export const isValidVatId = (vatId: string, countryCode: string): boolean => {
    const cleaned = vatId.replace(/\s+/g, '').toUpperCase();
    const format = vatFormats[countryCode];
    return format ? format.test(cleaned) : false;
}
